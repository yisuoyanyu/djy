package com.frame.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class TmplPrtUtil {

	public static byte[] prtByDoc(InputStream tmplStream, Map<String, Object> data) {
		
		byte[] result = null;
		
		try {
			
			HWPFDocument hwpfDoc = new HWPFDocument( tmplStream );			// 获得word文档对象
			
			// 处理页眉
			Range headerRange = hwpfDoc.getHeaderStoryRange();
			for ( String key : data.keySet() ) {
				Object value = data.get( key );
				if ( data.get(key) == null ) {
					headerRange.replaceText("${" + key + "}", "");
				} else if ( value.getClass().isArray() ) {
					
				} else {
					headerRange.replaceText("${" + key + "}", value.toString());
				}
			}
			
			// 处理内容
			Range bodyRange = hwpfDoc.getRange();
			for ( String key : data.keySet() ) {
				Object value = data.get( key );
				if ( data.get(key) == null ) {
					bodyRange.replaceText("${" + key + "}", "");
				} else if ( value.getClass().isArray() ) {
					
				} else {
					bodyRange.replaceText("${" + key + "}", value.toString());
				}
			}
			
			// 处理页脚
			Range footRange = hwpfDoc.getFootnoteRange();
			for ( String key : data.keySet() ) {
				Object value = data.get( key );
				if ( data.get(key) == null ) {
					footRange.replaceText("${" + key + "}", "");
				} else if ( value.getClass().isArray() ) {
					
				} else {
					footRange.replaceText("${" + key + "}", value.toString());
				}
			}
			
			// 将结果写入流
			ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			hwpfDoc.write( ostream );
			
			result = ostream.toByteArray();
			
			hwpfDoc.close();
			
		}  catch (Exception e) {
			System.out.println( e.getMessage() );
			e.printStackTrace();
		}
	
		return result;
	}
	
	
	
	public static byte[] prtByDocx(InputStream tmplStream, Map<String, Object> data) {
		
		byte[] result = null;
		
		try {
			
			XWPFDocument xwpfDoc = new XWPFDocument( tmplStream );
			
			// 处理页眉
			List<XWPFHeader> headers = xwpfDoc.getHeaderList();
			for ( XWPFHeader header : headers ) {
				for ( XWPFParagraph paragraph : header.getParagraphs() ) {
					dealParagraph( paragraph, data );
				}
			}
			
			
			// 处理段落
			List<XWPFParagraph> paragraphs =  xwpfDoc.getParagraphs();
			for ( XWPFParagraph paragraph : paragraphs ) {
				dealParagraph( paragraph, data );
			}
			
			// 处理表格
			List<XWPFTable> tables = xwpfDoc.getTables();
			for ( XWPFTable table : tables ) {
				List<XWPFTableRow> rows = table.getRows();
				for ( XWPFTableRow row : rows ) {
					List<XWPFTableCell> cells = row.getTableCells();
					for ( XWPFTableCell cell : cells ) {
						for ( XWPFParagraph paragraph : cell.getParagraphs() ) {
							dealParagraph( paragraph, data );
						}
					}
				}
			}
			
			// 处理页脚
			List<XWPFFooter> footers = xwpfDoc.getFooterList();
			for ( XWPFFooter footer : footers ) {
				for ( XWPFParagraph paragraph : footer.getParagraphs() ) {
					dealParagraph( paragraph, data );
				}
			}
			
			// 处理列表
			tables = xwpfDoc.getTables();
			for ( XWPFTable table : tables ) {
				List<XWPFTableRow> rows = table.getRows();
				int len = rows.size();
				for ( int rowIndex=len-1 ; rowIndex >=0 ; rowIndex-- ) {
					
					XWPFTableRow row = rows.get( rowIndex );
					
					boolean stop = false;	// 是否停止查找
					String key = null;
					List<XWPFTableCell> cells = row.getTableCells();
					for ( XWPFTableCell cell : cells ) {
						for ( XWPFParagraph paragraph : cell.getParagraphs() ) {
							if ( listMatcher( paragraph.getParagraphText() ).find() ) {
								// 防止关键词（$[……]）被分割为多个XWPRun
								List<XWPFRun> runs = paragraph.getRuns();
								for (int i=runs.size()-1; i>=0; i--) {
									String runText = runs.get(i).toString();
									if ( runText.contains("]") && !runText.contains("$[") && i>0 ) {
										runs.get(i-1).setText(runText);
										paragraph.removeRun(i);
									}
								}
								
								runs = paragraph.getRuns();
								for ( int i=0 ; i<runs.size() ; i++ ) {
									XWPFRun run = runs.get(i);
									String runText = run.toString();
									Matcher matcher = listMatcher(runText);
									if ( matcher.find() ) {
										do {
											String tmpKey = matcher.group(1);
											if ( key == null ) {
												key = tmpKey;
											} else if ( !key.equals(tmpKey) ) {
												// 跳到下一行
												stop = true;
												break;
											}
											runText = matcher.replaceFirst("");
										} while( ( matcher = objMatcher(runText) ).find() );
									}
									if ( stop ) break;
								}
								
							}
							
							if ( stop ) break;
						}
						
						if ( stop ) break;
					}
					
					if ( stop || key == null )
						continue;
					
					// 需要进行替换的行
					List<XWPFTableRow> destRows = new ArrayList<>();
					
					List< Map<String, Object> > keyData;
					if ( data.get(key) == null ) {
						keyData = null;
					} else {
						keyData = (ArrayList< Map<String, Object> >)data.get(key);
						for ( int i=0 ; i < keyData.size() - 1 ; i++ ) {
							// 当前行前面新增keyData.size()-1同样的行
							XWPFTableRow newRow = table.insertNewTableRow( rowIndex + 1 );
							copyRow( row, newRow );
							destRows.add( newRow );
						}
					}
					destRows.add( row );
					
					// 用keyData往destRows替换数据
					for ( int destIndex=0; destIndex < destRows.size(); destIndex++ ) {
						XWPFTableRow destRow = destRows.get( destIndex );
						List<XWPFTableCell> destCells = destRow.getTableCells();
						for ( XWPFTableCell destCell : destCells ) {
							for ( XWPFParagraph paragraph : destCell.getParagraphs() ) {
								if ( listMatcher( paragraph.getParagraphText() ).find() ) {
									List<XWPFRun> runs = paragraph.getRuns();
									for ( int i=0 ; i<runs.size() ; i++ ) {
										XWPFRun run = runs.get(i);
										String runText = run.toString();
										Matcher matcher = listMatcher(runText);
										if ( matcher.find() ) {
											do {
												String tmpKey = matcher.group(2);
												String value = "";
												if ( keyData != null ) {
													Map<String, Object> rowData = keyData.get( destIndex );
													if ( rowData != null ) {
														if ( rowData.get(tmpKey) != null )
															value = String.valueOf( rowData.get(tmpKey) );
													}
												}
												runText = matcher.replaceFirst(value);
											} while( ( matcher = objMatcher(runText) ).find() );
											paragraph.removeRun(i);
											paragraph.insertNewRun(i).setText(runText);
										}
									}
								}
							}
						}
					}
					
					
				}
			}
			
			// 将结果写入流
			ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			xwpfDoc.write( ostream );
						
			result = ostream.toByteArray();
			
			xwpfDoc.close();
			
		} catch (Exception e) {
			System.out.println( e.getMessage() );
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	// ------------------------------------------------------
	
	private static void dealParagraph( XWPFParagraph paragraph, Map<String, Object> data ) {
		if ( objMatcher( paragraph.getParagraphText() ).find() ) {
			// 防止关键词（${……}）被分割为多个XWPRun
			List<XWPFRun> runs = paragraph.getRuns();
			for ( int i=runs.size()-1 ; i>=0 ; i-- ) {
				String runText = runs.get(i).toString();
				if ( runText.contains("}") && !runText.contains("${") && i>0 ) {
					runs.get( i - 1 ).setText( runText );
					paragraph.removeRun(i);
				}
			}
			// 执行替换
			runs = paragraph.getRuns();
			for ( int i=0; i<runs.size(); i++ ) {
				XWPFRun run = runs.get(i);
				String runText = run.toString();
				Matcher matcher = objMatcher(runText);
				if ( matcher.find() ) {
					do {
						String key = matcher.group(1);
						Object value = data.get( key );
						runText = matcher.replaceFirst( value==null? "" : String.valueOf( value ) );
					} while( ( matcher = objMatcher(runText) ).find() );
					
					paragraph.removeRun(i);
					paragraph.insertNewRun(i).setText(runText);
				}
			}
			
		}
	}
	
	
	private static void copyRow(XWPFTableRow srcRow, XWPFTableRow destRow) {
		// 复制行属性
		destRow.getCtRow().setTrPr( srcRow.getCtRow().getTrPr() );
		
		List<XWPFTableCell> cellList = srcRow.getTableCells();
		if (null == cellList) {
			return;
		}
		
		// 添加单元格、复制单元格以及单元格内容
		XWPFTableCell destCell = null;
		for ( XWPFTableCell srcCell : cellList ) {
			
			destCell = destRow.addNewTableCell();
			
			// 设置新增单元格属性
			destCell.getCTTc().setTcPr( srcCell.getCTTc().getTcPr() );
			
			// 删除 新增单元格 的段落
			List<XWPFParagraph> destParagraphs = destCell.getParagraphs();
			for ( int i=destParagraphs.size()-1; i>=0; i-- ) {
				destCell.removeParagraph( i );
			}
			
			// 拷贝 源单元格内容 到 新增单元格
			List<XWPFParagraph> srcParagraphs = srcCell.getParagraphs();
			for ( XWPFParagraph srcParagraph : srcParagraphs ) {
				XWPFParagraph destParagraph = destCell.addParagraph();
				destParagraph.getCTP().setPPr( srcParagraph.getCTP().getPPr() );
				
				List<XWPFRun> srcRuns = srcParagraph.getRuns();
				for ( XWPFRun srcRun : srcRuns ) {
					XWPFRun destRun = destParagraph.createRun();
					destRun.setText( srcRun.toString() );
					destRun.setColor( srcRun.getColor() );
					destRun.setBold( srcRun.isBold() );
					destParagraph.addRun( destRun );
				}
			}
			
		}
	}
	
	
	
	// ------------------------------------------------------
	
	/**
	 * 正则匹配字符串
	 * @param str
	 * @return
	 */
	private static Matcher objMatcher(String str) {
		Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}
	
	private static Matcher listMatcher(String str) {
		Pattern pattern = Pattern.compile("\\$\\[(.+?)_(.+?)\\]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}
	
}
