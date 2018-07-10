
/**
 * 文件上传控件对象
 *  @param param {object} JSON数据
 *                  {
 *                      // 容器的id
 *                      'id' : {string}, 
 *                      
 *                      // 是否允许编辑。默认true。
 *                      // true — 允许编辑（上传、删除），false — 不允许编辑。
 *                      'edit' : {boolean}, 
 *                      
 *                      // 是否显示“上传”按钮。默认false。
 *                      // true — 显示，false — 不显示。
 *                      'showUpload' : {boolean}, 
 *                      
 *                      // 文件数限制（含已上传、未上传）。默认不限制。
 *                      'fileNumLimit' : {int}, 
 *                      
 *                      // 允许上传文件的后缀，多值用“|”分隔，如：“.gif|.jpg|.jpeg|.bmp|.png”
 *                      'acceptSuffix' : {string}, 
 *                      
 *                      // 临时上传文件夹属性
 *                      'tmpUpload' : {
 *                          
 *                          // 上传到临时文件夹的文件路径的参数名称（后端通过该参数名称取值）
 *                          'param' : {string}, 
 *                          
 *                          // 上传临时文件夹路径
 *                          'path' : {string}
 *                          
 *                      },
 *                      
 *                      // 目标文件夹属性
 *                      'uploadPath' : {
 *                          
 *                          // 目标文件夹参数名（后端通过该参数名称取值）
 *                          'param' : {string}, 
 *                          
 *                          // 目标文件夹路径值
 *                          'value' : {string}
 *                          
 *                      }, 
 *                      
 *                      // 原有文件属性
 *                      'initFile' : {
 *                          
 *                          // 原有文件的参数名称（后端通过该参数名称取值）
 *                          'param' : {string}, 
 *                          
 *                          // 初始化的文件列表
 *                          'files' : [
 *                              {
 *                                  // 文件标题
 *                                  'title' : {string},
 *                                  
 *                                  // 文件相对路径
 *                                  'filePath' : {string}, 
 *                                  
 *                                  // 文件url源路径
 *                                  'fileSrc' : {string}
 *                              }, 
 *                              ……
 *                          ]
 *                          
 *                      }
 *                      
 *                  }
 */
var FileUpload = function(param) {
    
    var oUpload = new Object();
    
    oUpload._id = param.id;
    
    oUpload._edit = typeof(param.edit)=='boolean' ? param.edit : true;
    
    oUpload._showUpload = typeof(param.showUpload)=='boolean' ? param.showUpload : false;
    
    oUpload._fileNumLimit = param.fileNumLimit;
    
    var acceptSuffix = param.acceptSuffix;
    if ( typeof(param.acceptSuffix) == 'undefined' || param.acceptSuffix==null || param.acceptSuffix=='' ) {
        
        oUpload._accept = null;
        
    } else {
        
        var mimes = {
                "ez": "application/andrew-inset", 
                "anx": "application/annodex", 
                "atom": "application/atom+xml", 
                "atomcat": "application/atomcat+xml", 
                "atomsrv": "application/atomserv+xml", 
                "lin": "application/bbolin", 
                "cap": "application/cap", 
                "pcap": "application/cap", 
                "cu": "application/cu-seeme", 
                "davmount": "application/davmount+xml", 
                "tsp": "application/dsptype", 
                "es": "application/ecmascript", 
                "spl": "application/futuresplash", 
                "hta": "application/hta", 
                "jar": "application/java-archive", 
                "ser": "application/java-serialized-object", 
                "class": "application/java-vm", 
                "js": "application/javascript", 
                "m3g": "application/m3g", 
                "hqx": "application/mac-binhex40", 
                "cpt": "application/mac-compactpro", 
                "nb": "application/mathematica", 
                "nbp": "application/mathematica", 
                "mdb": "application/msaccess", 
                "doc": "application/msword", 
                "dot": "application/msword", 
                "mxf": "application/mxf", 
                "bin": "application/octet-stream", 
                "oda": "application/oda", 
                "ogx": "application/ogg", 
                "pdf": "application/pdf", 
                "key": "application/pgp-keys", 
                "pgp": "application/pgp-signature", 
                "prf": "application/pics-rules", 
                "ps": "application/postscript", 
                "ai": "application/postscript", 
                "eps": "application/postscript", 
                "epsi": "application/postscript", 
                "epsf": "application/postscript", 
                "eps2": "application/postscript", 
                "eps3": "application/postscript", 
                "rar": "application/rar", 
                "rdf": "application/rdf+xml", 
                "rss": "application/rss+xml", 
                "rtf": "application/rtf", 
                "smi": "application/smil", 
                "smil": "application/smil", 
                "xhtml": "application/xhtml+xml", 
                "xht": "application/xhtml+xml", 
                "xml": "application/xml", 
                "xsl": "application/xml", 
                "xsd": "application/xml", 
                "xspf": "application/xspf+xml", 
                "zip": "application/zip", 
                "apk": "application/vnd.android.package-archive", 
                "cdy": "application/vnd.cinderella", 
                "kml": "application/vnd.google-earth.kml+xml", 
                "kmz": "application/vnd.google-earth.kmz", 
                "xul": "application/vnd.mozilla.xul+xml", 
                "xls": "application/vnd.ms-excel", 
                "xlb": "application/vnd.ms-excel", 
                "xlt": "application/vnd.ms-excel", 
                "cat": "application/vnd.ms-pki.seccat", 
                "stl": "application/vnd.ms-pki.stl", 
                "ppt": "application/vnd.ms-powerpoint", 
                "pps": "application/vnd.ms-powerpoint", 
                "xlsx": "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", 
                "xltx": "application/vnd.openxmlformats-officedocument.spreadsheetml.template", 
                "pptx": "application/vnd.openxmlformats-officedocument.presentationml.presentation", 
                "ppsx": "application/vnd.openxmlformats-officedocument.presentationml.slideshow", 
                "potx": "application/vnd.openxmlformats-officedocument.presentationml.template", 
                "docx": "application/vnd.openxmlformats-officedocument.wordprocessingml.document", 
                "dotx": "application/vnd.openxmlformats-officedocument.wordprocessingml.template", 
                "cod": "application/vnd.rim.cod", 
                "mmf": "application/vnd.smaf", 
                "sis": "application/vnd.symbian.install", 
                "vsd": "application/vnd.visio", 
                "wbxml": "application/vnd.wap.wbxml", 
                "wmlc": "application/vnd.wap.wmlc", 
                "wmlsc": "application/vnd.wap.wmlscriptc", 
                "wpd": "application/vnd.wordperfect", 
                "wp5": "application/vnd.wordperfect5.1", 
                "wk": "application/x-123", 
                "7z": "application/x-7z-compressed", 
                "abw": "application/x-abiword", 
                "dmg": "application/x-apple-diskimage", 
                "bcpio": "application/x-bcpio", 
                "torrent": "application/x-bittorrent", 
                "cab": "application/x-cab", 
                "cbr": "application/x-cbr", 
                "cbz": "application/x-cbz", 
                "cdf": "application/x-cdf", 
                "cda": "application/x-cdf", 
                "vcd": "application/x-cdlink", 
                "pgn": "application/x-chess-pgn", 
                "cpio": "application/x-cpio", 
                "csh": "application/x-csh", 
                "deb": "application/x-debian-package", 
                "udeb": "application/x-debian-package", 
                "dcr": "application/x-director", 
                "dir": "application/x-director", 
                "dxr": "application/x-director", 
                "dms": "application/x-dms", 
                "wad": "application/x-doom", 
                "dvi": "application/x-dvi", 
                "rhtml": "application/x-httpd-eruby", 
                "pfa": "application/x-font", 
                "pfb": "application/x-font", 
                "gsf": "application/x-font", 
                "pcf": "application/x-font", 
                "pcf.Z": "application/x-font", 
                "mm": "application/x-freemind", 
                "spl": "application/x-futuresplash", 
                "gnumeric": "application/x-gnumeric", 
                "sgf": "application/x-go-sgf", 
                "gcf": "application/x-graphing-calculator", 
                "gtar": "application/x-gtar", 
                "tgz": "application/x-gtar", 
                "taz": "application/x-gtar", 
                "hdf": "application/x-hdf", 
                "phtml": "application/x-httpd-php", 
                "pht": "application/x-httpd-php", 
                "php": "application/x-httpd-php", 
                "phps": "application/x-httpd-php-source", 
                "php3": "application/x-httpd-php3", 
                "php3p": "application/x-httpd-php3-preprocessed", 
                "php4": "application/x-httpd-php4", 
                "php5": "application/x-httpd-php5", 
                "ica": "application/x-ica", 
                "info": "application/x-info", 
                "ins": "application/x-internet-signup", 
                "isp": "application/x-internet-signup", 
                "iii": "application/x-iphone", 
                "iso": "application/x-iso9660-image", 
                "jam": "application/x-jam", 
                "jnlp": "application/x-java-jnlp-file", 
                "jmz": "application/x-jmol", 
                "chrt": "application/x-kchart", 
                "kil": "application/x-killustrator", 
                "skp": "application/x-koan", 
                "skd": "application/x-koan", 
                "skt": "application/x-koan", 
                "skm": "application/x-koan", 
                "kpr": "application/x-kpresenter", 
                "kpt": "application/x-kpresenter", 
                "ksp": "application/x-kspread", 
                "kwd": "application/x-kword", 
                "kwt": "application/x-kword", 
                "latex": "application/x-latex", 
                "lha": "application/x-lha", 
                "lyx": "application/x-lyx", 
                "lzh": "application/x-lzh", 
                "lzx": "application/x-lzx", 
                "frm": "application/x-maker", 
                "maker": "application/x-maker", 
                "frame": "application/x-maker", 
                "fm": "application/x-maker", 
                "fb": "application/x-maker", 
                "book": "application/x-maker", 
                "fbdoc": "application/x-maker", 
                "mif": "application/x-mif", 
                "wmd": "application/x-ms-wmd", 
                "wmz": "application/x-ms-wmz", 
                "com": "application/x-msdos-program", 
                "exe": "application/x-msdos-program", 
                "bat": "application/x-msdos-program", 
                "dll": "application/x-msdos-program", 
                "msi": "application/x-msi", 
                "nc": "application/x-netcdf", 
                "pac": "application/x-ns-proxy-autoconfig", 
                "dat": "application/x-ns-proxy-autoconfig", 
                "nwc": "application/x-nwc", 
                "o": "application/x-object", 
                "oza": "application/x-oz-application", 
                "p7r": "application/x-pkcs7-certreqresp", 
                "crl": "application/x-pkcs7-crl", 
                "pyc": "application/x-python-code", 
                "pyo": "application/x-python-code", 
                "qgs": "application/x-qgis", 
                "shp": "application/x-qgis", 
                "shx": "application/x-qgis", 
                "qtl": "application/x-quicktimeplayer", 
                "rpm": "application/x-redhat-package-manager", 
                "rb": "application/x-ruby", 
                "sh": "application/x-sh", 
                "shar": "application/x-shar", 
                "swf": "application/x-shockwave-flash", 
                "swfl": "application/x-shockwave-flash", 
                "scr": "application/x-silverlight", 
                "sit": "application/x-stuffit", 
                "sitx": "application/x-stuffit", 
                "sv4cpio": "application/x-sv4cpio", 
                "sv4crc": "application/x-sv4crc", 
                "tar": "application/x-tar", 
                "tcl": "application/x-tcl", 
                "gf": "application/x-tex-gf", 
                "pk": "application/x-tex-pk", 
                "texinfo": "application/x-texinfo", 
                "texi": "application/x-texinfo", 
                "~": "application/x-trash", 
                "%": "application/x-trash", 
                "bak": "application/x-trash", 
                "old": "application/x-trash", 
                "sik": "application/x-trash", 
                "t": "application/x-troff", 
                "tr": "application/x-troff", 
                "roff": "application/x-troff", 
                "man": "application/x-troff-man", 
                "me": "application/x-troff-me", 
                "ms": "application/x-troff-ms", 
                "ustar": "application/x-ustar", 
                "src": "application/x-wais-source", 
                "wz": "application/x-wingz", 
                "crt": "application/x-x509-ca-cert", 
                "xcf": "application/x-xcf", 
                "fig": "application/x-xfig", 
                "xpi": "application/x-xpinstall", 
                "amr": "audio/amr", 
                "awb": "audio/amr-wb", 
                "amr": "audio/amr", 
                "awb": "audio/amr-wb", 
                "axa": "audio/annodex", 
                "au": "audio/basic", 
                "snd": "audio/basic", 
                "flac": "audio/flac", 
                "mid": "audio/midi", 
                "midi": "audio/midi", 
                "kar": "audio/midi", 
                "mpga": "audio/mpeg", 
                "mpega": "audio/mpeg", 
                "mp2": "audio/mpeg", 
                "mp3": "audio/mpeg", 
                "m4a": "audio/mpeg", 
                "m3u": "audio/mpegurl", 
                "oga": "audio/ogg", 
                "ogg": "audio/ogg", 
                "spx": "audio/ogg", 
                "sid": "audio/prs.sid", 
                "aif": "audio/x-aiff", 
                "aiff": "audio/x-aiff", 
                "aifc": "audio/x-aiff", 
                "gsm": "audio/x-gsm", 
                "m3u": "audio/x-mpegurl", 
                "wma": "audio/x-ms-wma", 
                "wax": "audio/x-ms-wax", 
                "ra": "audio/x-pn-realaudio", 
                "rm": "audio/x-pn-realaudio", 
                "ram": "audio/x-pn-realaudio", 
                "ra": "audio/x-realaudio", 
                "pls": "audio/x-scpls", 
                "sd2": "audio/x-sd2", 
                "wav": "audio/x-wav", 
                "alc": "chemical/x-alchemy", 
                "cac": "chemical/x-cache", 
                "cache": "chemical/x-cache", 
                "csf": "chemical/x-cache-csf", 
                "cbin": "chemical/x-cactvs-binary", 
                "cascii": "chemical/x-cactvs-binary", 
                "ctab": "chemical/x-cactvs-binary", 
                "cdx": "chemical/x-cdx", 
                "cer": "chemical/x-cerius", 
                "c3d": "chemical/x-chem3d", 
                "chm": "chemical/x-chemdraw", 
                "cif": "chemical/x-cif", 
                "cmdf": "chemical/x-cmdf", 
                "cml": "chemical/x-cml", 
                "cpa": "chemical/x-compass", 
                "bsd": "chemical/x-crossfire", 
                "csml": "chemical/x-csml", 
                "csm": "chemical/x-csml", 
                "ctx": "chemical/x-ctx", 
                "cxf": "chemical/x-cxf", 
                "cef": "chemical/x-cxf", 
                "smi": "#chemical/x-daylight-smiles", 
                "emb": "chemical/x-embl-dl-nucleotide", 
                "embl": "chemical/x-embl-dl-nucleotide", 
                "spc": "chemical/x-galactic-spc", 
                "inp": "chemical/x-gamess-input", 
                "gam": "chemical/x-gamess-input", 
                "gamin": "chemical/x-gamess-input", 
                "fch": "chemical/x-gaussian-checkpoint", 
                "fchk": "chemical/x-gaussian-checkpoint", 
                "cub": "chemical/x-gaussian-cube", 
                "gau": "chemical/x-gaussian-input", 
                "gjc": "chemical/x-gaussian-input", 
                "gjf": "chemical/x-gaussian-input", 
                "gal": "chemical/x-gaussian-log", 
                "gcg": "chemical/x-gcg8-sequence", 
                "gen": "chemical/x-genbank", 
                "hin": "chemical/x-hin", 
                "istr": "chemical/x-isostar", 
                "ist": "chemical/x-isostar", 
                "jdx": "chemical/x-jcamp-dx", 
                "dx": "chemical/x-jcamp-dx", 
                "kin": "chemical/x-kinemage", 
                "mcm": "chemical/x-macmolecule", 
                "mmd": "chemical/x-macromodel-input", 
                "mmod": "chemical/x-macromodel-input", 
                "mol": "chemical/x-mdl-molfile", 
                "rd": "chemical/x-mdl-rdfile", 
                "rxn": "chemical/x-mdl-rxnfile", 
                "sd": "chemical/x-mdl-sdfile", 
                "sdf": "chemical/x-mdl-sdfile", 
                "tgf": "chemical/x-mdl-tgf", 
                "mif": "#chemical/x-mif", 
                "mcif": "chemical/x-mmcif", 
                "mol2": "chemical/x-mol2", 
                "b": "chemical/x-molconn-Z", 
                "gpt": "chemical/x-mopac-graph", 
                "mop": "chemical/x-mopac-input", 
                "mopcrt": "chemical/x-mopac-input", 
                "mpc": "chemical/x-mopac-input", 
                "zmt": "chemical/x-mopac-input", 
                "moo": "chemical/x-mopac-out", 
                "mvb": "chemical/x-mopac-vib", 
                "asn": "chemical/x-ncbi-asn1", 
                "prt": "chemical/x-ncbi-asn1-ascii", 
                "ent": "chemical/x-ncbi-asn1-ascii", 
                "val": "chemical/x-ncbi-asn1-binary", 
                "aso": "chemical/x-ncbi-asn1-binary", 
                "asn": "chemical/x-ncbi-asn1-spec", 
                "pdb": "chemical/x-pdb", 
                "ent": "chemical/x-pdb", 
                "ros": "chemical/x-rosdal", 
                "sw": "chemical/x-swissprot", 
                "vms": "chemical/x-vamas-iso14976", 
                "vmd": "chemical/x-vmd", 
                "xtel": "chemical/x-xtel", 
                "xyz": "chemical/x-xyz", 
                "gif": "image/gif", 
                "ief": "image/ief", 
                "jpeg": "image/jpeg", 
                "jpg": "image/jpeg", 
                "jpe": "image/jpeg", 
                "pcx": "image/pcx", 
                "png": "image/png", 
                "svg": "image/svg+xml", 
                "svgz": "image/svg+xml", 
                "tiff": "image/tiff", 
                "tif": "image/tiff", 
                "djvu": "image/vnd.djvu", 
                "djv": "image/vnd.djvu", 
                "wbmp": "image/vnd.wap.wbmp", 
                "cr2": "image/x-canon-cr2", 
                "crw": "image/x-canon-crw", 
                "ras": "image/x-cmu-raster", 
                "cdr": "image/x-coreldraw", 
                "pat": "image/x-coreldrawpattern", 
                "cdt": "image/x-coreldrawtemplate", 
                "cpt": "image/x-corelphotopaint", 
                "erf": "image/x-epson-erf", 
                "ico": "image/x-icon", 
                "art": "image/x-jg", 
                "jng": "image/x-jng", 
                "bmp": "image/x-ms-bmp", 
                "nef": "image/x-nikon-nef", 
                "orf": "image/x-olympus-orf", 
                "psd": "image/x-photoshop", 
                "pnm": "image/x-portable-anymap", 
                "pbm": "image/x-portable-bitmap", 
                "pgm": "image/x-portable-graymap", 
                "ppm": "image/x-portable-pixmap", 
                "rgb": "image/x-rgb", 
                "xbm": "image/x-xbitmap", 
                "xpm": "image/x-xpixmap", 
                "xwd": "image/x-xwindowdump", 
                "eml": "message/rfc822", 
                "igs": "model/iges", 
                "iges": "model/iges", 
                "msh": "model/mesh", 
                "mesh": "model/mesh", 
                "silo": "model/mesh", 
                "wrl": "model/vrml", 
                "vrml": "model/vrml", 
                "x3dv": "model/x3d+vrml", 
                "x3d": "model/x3d+xml", 
                "x3db": "model/x3d+binary", 
                "manifest": "text/cache-manifest", 
                "ics": "text/calendar", 
                "icz": "text/calendar", 
                "css": "text/css", 
                "csv": "text/csv", 
                "323": "text/h323", 
                "html": "text/html", 
                "htm": "text/html", 
                "shtml": "text/html", 
                "uls": "text/iuls", 
                "mml": "text/mathml", 
                "asc": "text/plain", 
                "txt": "text/plain", 
                "text": "text/plain", 
                "pot": "text/plain", 
                "brf": "text/plain", 
                "rtx": "text/richtext", 
                "sct": "text/scriptlet", 
                "wsc": "text/scriptlet", 
                "tm": "text/texmacs", 
                "ts": "text/texmacs", 
                "tsv": "text/tab-separated-values", 
                "jad": "text/vnd.sun.j2me.app-descriptor", 
                "wml": "text/vnd.wap.wml", 
                "wmls": "text/vnd.wap.wmlscript", 
                "bib": "text/x-bibtex", 
                "boo": "text/x-boo", 
                "h++": "text/x-c++hdr", 
                "hpp": "text/x-c++hdr", 
                "hxx": "text/x-c++hdr", 
                "hh": "text/x-c++hdr", 
                "c++": "text/x-c++src", 
                "cpp": "text/x-c++src", 
                "cxx": "text/x-c++src", 
                "cc": "text/x-c++src", 
                "h": "text/x-chdr", 
                "htc": "text/x-component", 
                "csh": "text/x-csh", 
                "c": "text/x-csrc", 
                "d": "text/x-dsrc", 
                "diff": "text/x-diff", 
                "patch": "text/x-diff", 
                "hs": "text/x-haskell", 
                "java": "text/x-java", 
                "lhs": "text/x-literate-haskell", 
                "moc": "text/x-moc", 
                "p": "text/x-pascal", 
                "pas": "text/x-pascal", 
                "gcd": "text/x-pcs-gcd", 
                "pl": "text/x-perl", 
                "pm": "text/x-perl", 
                "py": "text/x-python", 
                "scala": "text/x-scala", 
                "etx": "text/x-setext", 
                "sh": "text/x-sh", 
                "tcl": "text/x-tcl", 
                "tk": "text/x-tcl", 
                "tex": "text/x-tex", 
                "ltx": "text/x-tex", 
                "sty": "text/x-tex", 
                "cls": "text/x-tex", 
                "vcs": "text/x-vcalendar", 
                "vcf": "text/x-vcard", 
                "3gp": "video/3gpp", 
                "axv": "video/annodex", 
                "dl": "video/dl", 
                "dif": "video/dv", 
                "dv": "video/dv", 
                "fli": "video/fli", 
                "gl": "video/gl", 
                "mpeg": "video/mpeg", 
                "mpg": "video/mpeg", 
                "mpe": "video/mpeg", 
                "mp4": "video/mp4", 
                "qt": "video/quicktime", 
                "mov": "video/quicktime", 
                "ogv": "video/ogg", 
                "mxu": "video/vnd.mpegurl", 
                "flv": "video/x-flv", 
                "lsf": "video/x-la-asf", 
                "lsx": "video/x-la-asf", 
                "mng": "video/x-mng", 
                "asf": "video/x-ms-asf", 
                "asx": "video/x-ms-asf", 
                "wm": "video/x-ms-wm", 
                "wmv": "video/x-ms-wmv", 
                "wmx": "video/x-ms-wmx", 
                "wvx": "video/x-ms-wvx", 
                "avi": "video/x-msvideo", 
                "movie": "video/x-sgi-movie", 
                "mpv": "video/x-matroska", 
                "mkv": "video/x-matroska", 
                "ice": "x-conference/x-cooltalk", 
                "sisx": "x-epoc/x-sisx-app", 
                "vrm": "x-world/x-vrml", 
                "vrml": "x-world/x-vrml", 
                "wrl": "x-world/x-vrml"
            };
        
        var suffixs = acceptSuffix.split('|');
        var extensions = new Array();
        var mimeTypes = new Array();
        
        for (var i=0; i<suffixs.length; i++) {
            var suffix = suffixs[i]
            var extension = suffix.substr(suffix.lastIndexOf('.') + 1);
            extensions.push(extension);
            mimeTypes.push( mimes[extension] );
        }
        
        oUpload._accept = {
            // 文字描述
            'title' : 'Files', 
            // 允许的文件后缀，不带点，多个用逗号分割。
            'extensions' : extensions.join(','), 
            // 多个用逗号分割。
            'mimeTypes' : mimeTypes.join(',')
        };
        
    }
    
    oUpload._tmpUpload = param.tmpUpload;
    oUpload._uploadPath = param.uploadPath;
    oUpload._initFile = param.initFile;
    
    oUpload._uploader;
    
    oUpload._isSubmit;
    oUpload._submitSuccess;
    oUpload._submitError;
    
    
    oUpload._getInitFileCount = function() {
        if (oUpload._initFile) {
            var files = oUpload._initFile.files;
            if ($.isArray(files))
                return files.length;
        }
        return 0;
    };
    
    oUpload._oldFileCount = 0;
    
    
    // 状态：
    // pedding：等待添加
    // ready：准备上传
    // uploading：上传中
    // paused: 暂停上传
    // confirm： 上传结束
    // finish：上传成功
    // done：上传失败
    oUpload._state = 'pedding';
    
    
    /**
     * 初始化
     */
    oUpload.init = function() {
        
        var $fileUpload = $('#' + oUpload._id);
        
        $fileUpload.addClass('fileUpload');
        
        $('<input name="fileUploadId" type="hidden" value="' + oUpload._id + '" />').appendTo( $fileUpload );
        $('<input name="' + oUpload._uploadPath.param + '" type="hidden" value="' + oUpload._uploadPath.value + '" />').appendTo( $fileUpload );
        
        var $container = $('<div class="container"></div>').appendTo( $fileUpload );
        
        
        //---------------------------------------------------------------------------
        if ( !WebUploader.Uploader.support('flash') && WebUploader.browser.ie ) {
            
            // 检测是否已经安装flash，检测flash的版本
            var flashVersion = ( function() {
                var version;
                
                try {
                    version = navigator.plugins[ 'Shockwave Flash' ];
                    version = version.description;
                } catch ( ex ) {
                    try {
                        version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash').GetVariable('$version');
                    } catch ( ex2 ) {
                        version = '0.0';
                    }
                }
                version = version.match( /\d+/g );
                
                return parseFloat( version[ 0 ] + '.' + version[ 1 ], 10 );
            } )();
            
            // flash 安装了但是版本过低。
            if ( flashVersion ) {
                (function(container) {
                    window['expressinstallcallback'] = function( state ) {
                        switch(state) {
                            case 'Download.Cancelled':
                                alert('您取消了更新！');
                                break;
                                
                            case 'Download.Failed':
                                alert('安装失败');
                                break;
                                
                            default:
                                alert('安装已成功，请刷新！');
                                break;
                                
                        }
                        delete window['expressinstallcallback'];
                    };
                    
                    var swf = './expressInstall.swf';
                    
                    // insert flash object
                    var html = '<object type="application/x-shockwave-flash" data="' +  swf + '" ';
                    
                    if (WebUploader.browser.ie) {
                        html += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ';
                    }
                    
                    html += 'width="100%" height="100%" style="outline:0">' 
                        + '<param name="movie" value="' + swf + '" />' 
                        + '<param name="wmode" value="transparent" />' 
                        + '<param name="allowscriptaccess" value="always" />' 
                        + '</object>';
                    
                    container.html(html);
                    
                })( $container );
                
            } else {    // 压根就没有安转。
                $container.html('<a href="http://www.adobe.com/go/getflashplayer" target="_blank" border="0"><img alt="get flash player" src="http://www.adobe.com/macromedia/style_guide/images/160x41_Get_Flash_Player.jpg" /></a>');
            }
            
            return;
            
        } else if (!WebUploader.Uploader.support()) {
            alert( 'Web Uploader 不支持您的浏览器！');
            return;
        }
        //---------------------------------------------------------------------------
        
        
        var $uploader = $('<div id="' + oUpload._id + '_uploader" + class="uploader"></div>').appendTo( $container );
        
        // 文件列表
        var $list = $('<div class="list"></div>').appendTo( $uploader );
        
        // 按钮条
        var $btns = $('<div class="btns" style="display:none;"></div>').appendTo( $uploader );
        
        // “添加文件”按钮
        var $picker = $(
              '<div id="' + oUpload._id + '_filePicker" class="filePicker">' 
            + ( oUpload._fileNumLimit==1 ? '选择文件' : '添加文件' ) 
            + '</div>'
            ).appendTo( $btns );
        
        
        if ( oUpload._showUpload ) {
            //（注：按钮的不能用<button></button>，不然会上传失败，应该是webupload控件的bug）
            $('<div class="uploadBtn">开始上传</div>').appendTo( $btns );
        }
        
        // “上传”按钮
        var $uploadBtn = $btns.find('.uploadBtn');
        
        // 添加的文件数量
        var fileCount = 0;
        
        // 添加的文件总大小
        var fileSize = 0;
        
        // 优化retina, 在retina下这个值是2
        var ratio = window.devicePixelRatio || 1;
        
        // 缩略图大小
        var thumbnailWidth = 110 * ratio;
        var thumbnailHeight = 110 * ratio;
        
        // 所有文件的进度信息，key为file id
        var percentages = {};
        
        
        // 实例化
        var uploader = WebUploader.create( {
            
            // swf文件 路径
            swf: 'plugins/upload/webuploader/Uploader.swf', 
            
            // 禁掉全局的拖拽功能。这样不会出现文件拖进页面的时候，把文件打开。
            disableGlobalDnd: true,
            
            // 指定“添加文件”的按钮容器，不指定则不创建按钮。
            pick: {
                
                // 指定选择文件的按钮容器，不指定则不创建按钮。注意 这里虽然写的是 id, 但是不是只支持 id, 还支持 class, 或者 dom 节点。
                id: '#' + oUpload._id + '_filePicker', 
                
                // 请采用 innerHTML 代替
                innerHTML: ( oUpload._fileNumLimit==1 ? '选择文件' : '添加文件' ) 
                
            }, 
            
            
            // 指定接受哪些类型的文件。 由于目前还有ext转mimeType表，所以这里需要分开指定。
            accept: oUpload._accept, 
            
            // 配置压缩的图片的选项。如果此选项为false, 则图片在上传前不进行压缩。 
            compress: false, 
            
            // 文件接收服务端
            server: 'fileUpload/webuploader.json', 
            
            // 文件上传请求的参数表，每次发送都会发送此对象中的参数。
            formData: {
                'path' : oUpload._tmpUpload.path
            }, 
            
            // 是否要分片处理大文件上传。
            chunked: false, 
            
            // 如果要分片，分多大一片？ 默认大小为5M。
            chunkSize: 512 * 1024, 
            
            // 验证文件总数量, 超出则不允许加入队列。
            fileNumLimit: oUpload._fileNumLimit, 
            
        } );
        
        
        // 拖拽时不接受 js 文件。
        uploader.on( 'dndAccept', function( items ) {
            var denied = false;
            
            var unAllowed = 'application/javascript';   // 修改js类型
            
            for (var i=0; i < items.length; i++ ) {
                // 如果在列表里面
                if ( ~unAllowed.indexOf( items[ i ].type ) ) {
                    denied = true;
                    break;
                }
            }
            
            return !denied;
        } );
        
        
        // 添加 初始化文件
        function addInitFile( param ) {
            
            var $item = $( 
                  '<div class="item">' 
                + '    <p class="title">' + param.title + '</p>' 
                + '    <div class="btns"></div>' 
                + '</div>' );
            
            var $hidden = $('<input name="' + oUpload._initFile.param + '" type="hidden" value="' + param.filePath + '"/>').appendTo( $item );
            
            var $btns = $item.find('.btns');
            
            // “查看” 按钮
            var $btnRead = $(
                  '<span class="read">' 
                + '<a href="' + param.fileSrc + '" target="_blank">查看</a>' 
                + '</span>' 
                ).appendTo( $btns );
            
            // “下载” 按钮
            var $btnDownload = $(
                  '<span class="download">' 
                + '<a href="' + param.fileSrc + '?download=1&saveAs=' + encodeURIComponent(param.title) + '" target="_blank">下载</a>' 
                + '</span>' 
                ).appendTo( $btns );
            
            if ( oUpload._edit ) {
                // “删除” 按钮
                var $btnCancel = $('<span class="cancel">删除</span>').appendTo( $btns );
                $btnCancel.on( 'click', function() {
                    $item.off().find('.btns span').off().end().remove();
                    oUpload._oldFileCount--;
                } );
            }
            
            $item.appendTo( $list );
            oUpload._oldFileCount ++;
        }
        
        if ( oUpload._edit ) {
            $uploadBtn.addClass( 'hidden' );
        }
        
        // 初始化文件数量大于0时，初始化文件列表
        if ( oUpload._getInitFileCount() > 0 ) {
            var initFiles = oUpload._initFile.files;
            for ( var i=0; i<initFiles.length; i++ ) {
                addInitFile( initFiles[i] );
            }
            
            
        }
        
        if ( oUpload._edit ) {
            $btns.show();
        }
        
        
        // 当有文件添加进来时执行，负责view的创建
        function addFile( file ) {
            
            var $item = $( '<div id="' + file.id + '" class="item">' 
                    + '<p class="title">' + file.name + '</p>' 
                    + '<p class="progress"><span></span></p>' 
                    + '<div class="btns">'
                    + '<span class="cancel">删除</span>'
                    + '</div>'
                    + '</div>' );
            
            var $hidden = $('<input id="' + file.id + '_hidden" name="' + oUpload._tmpUpload.param + '" type="hidden" />').appendTo( $item );
            
            var $percent = $item.find('p.progress span');
            
            var $btnCancel = $item.find('div.btns span.cancel');
            $btnCancel.on( 'click', function() {
                uploader.removeFile( file );
            });
            
            
            $item.appendTo( $list );
            
            var $error = $('<p class="error"></p>');
            
            var showError = function( code ) {
                switch( code ) {
                    case 'exceed_size':
                        text = '文件大小超出';
                        break;
                        
                    case 'interrupt':
                        text = '上传暂停';
                        break;
                        
                    default:
                        text = '上传失败，请重试';
                        break;
                }
                $error.text( text ).appendTo( $item );
            };
            
            if ( file.getStatus() === 'invalid' ) {
                showError( file.statusText );
            } else {
                percentages[ file.id ] = [ file.size, 0 ];
            }
            
            
            // 文件状态（File.Status）
            // inited 初始状态
            // queued 已经进入队列, 等待上传
            // progress 上传中
            // complete 上传完成
            // error 上传出错，可重试
            // interrupt 上传中断，可续传
            // invalid 文件不合格，不能重试上传。会自动从队列中移除。
            // cancelled 文件被移除
            file.on( 'statuschange', function( cur, prev ) {
                
                if ( prev === 'progress' ) {
                    $percent.closest('.progress').hide();
                    $percent.hide().width(0);
                }
                
                // 成功
                if ( cur === 'error' || cur === 'invalid' ) {
                    console.log( file.statusText );
                    showError( file.statusText );
                    percentages[ file.id ][ 1 ] = 1;
                } else if ( cur === 'interrupt' ) {     // 上传中断，可续传
                    showError( 'interrupt' );
                } else if ( cur === 'queued' ) {        // 已经进入队列, 等待上传
                    percentages[ file.id ][ 1 ] = 0;
                } else if ( cur === 'progress' ) {      // 上传中
                    $error.remove();
                    $percent.closest('.progress').css('display', 'block');
                    $percent.css('display', 'block');
                } else if ( cur === 'complete' ) {      // 上传完成
                    
                }
                
                $item.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
            } );
        }
        
        
        // 当文件删除时执行，负责view的销毁
        function removeFile( file ) {
            var $item = $('#' + file.id);
            delete percentages[ file.id ];
            $item.off().find('.btns span').off().end().remove();
        }
        
        
        
        function setState( val ) {
            
            if ( val === oUpload._state )
                return;
            
            $uploadBtn.removeClass( 'state-' + oUpload._state );
            $uploadBtn.addClass( 'state-' + val );
            
            oUpload._state = val;
            
            switch ( oUpload._state ) {
            
                case 'pedding':
                    if (oUpload._oldFileCount == 0) {
                        uploader.refresh();
                    }
                    break;
                    
                case 'ready':
                    uploader.refresh();
                    break;
                    
                case 'uploading':
                    $uploadBtn.text( '暂停上传' );
                    break;
                    
                case 'paused':
                    $uploadBtn.text( '继续上传' );
                    break;
                    
                case 'confirm':
                    $uploadBtn.text( '开始上传' );
                    
                    var stats = uploader.getStats();
                    if ( stats.successNum && !stats.uploadFailNum ) {
                        setState( 'finish' );
                        return;
                    }
                    break;
                    
                case 'finish':
                    var stats = uploader.getStats();
                    if ( stats.successNum ) {
                        if ( oUpload._showUpload ) {
                            Alert( '上传成功' );
                        }
                        if (oUpload._isSubmit && $.isFunction(oUpload._submitSuccess)) {
                            oUpload._submitSuccess.call();
                        }
                    } else {
                        // 没有成功的图片，重设
                        oUpload._state = 'done';
                        if (oUpload._isSubmit && $.isFunction(oUpload._submitError)) {
                            oUpload._submitError.call();
                        }
                    }
                    break;
            }
            
        }
        
        
        // 当文件被加入队列之前触发，此事件的handler返回值为false，则此文件不会被添加进入队列。
        uploader.on( 'beforeFileQueued', function( file ) {
            if ( typeof(oUpload._fileNumLimit)!='undefined' && oUpload._fileNumLimit != null ) {
                if ( oUpload._fileNumLimit == 1 ) {
                    
                    // 删除现有文件
                    // TODO （遗留问题：在IE下只执行一次trigger('click')不能生效，未查不出原因。）
                    $list.find('.btns .cancel').trigger('click').trigger('click');
                    
                    return true;
                } else {
                    if ( oUpload._oldFileCount + fileCount >= oUpload._fileNumLimit ) {
                        Alert('文件数量超出！');
                        return false;
                    }
                }
            }
            return true;
        } );
        
        
        // 当文件被加入队列以后触发。
        uploader.on( 'fileQueued', function( file ) {
            fileCount++;
            fileSize += file.size;
            
            if ( fileCount === 1 ) {
                $uploadBtn.removeClass( 'hidden' );
            }
            
            addFile( file );
            setState( 'ready' );
        } );
        
        
        // 当文件被移除队列后触发。
        uploader.on( 'fileDequeued', function( file ) {
            fileCount--;
            fileSize -= file.size;
            
            if ( !fileCount ) {
                setState( 'pedding' );
                $uploadBtn.addClass( 'hidden' );
            }
            
            removeFile( file );
        } );
        
        
        // 上传过程中触发，携带上传进度。
        uploader.on( 'uploadProgress', function( file, percentage ) {
            percentages[ file.id ][ 1 ] = percentage;
        } );
        
        
        // 当文件上传成功时触发。
        uploader.on( 'uploadSuccess', function( file, response ) {
            $('#' + file.id + '_hidden').val( file.name + "|" + response.filePath );
        } );
        
        
        // 特殊事件all，所有的事件触发都会响应到。
        uploader.on( 'all', function( type ) {
            
            switch( type ) {
                
                // 当开始上传流程时触发。
                case 'startUpload':
                    setState( 'uploading' );
                    break;
                    
                // 当开始上传流程暂停时触发。
                case 'stopUpload':
                    setState( 'paused' );
                    break;
                    
                // 当所有文件上传结束时触发。
                case 'uploadFinished':
                    setState( 'confirm' );
                    break;
                    
            }
            
        } );
        
        
        // 当validate不通过时，会以派送错误事件的形式通知调用者。通过upload.on('error', handler)可以捕获到此类错误。
        uploader.on( 'error', function( type ) {
            if ( type === 'F_DUPLICATE' ) {
                Alert('重复选择文件！');
            } else if ( type === 'Q_EXCEED_NUM_LIMIT' ) {
                Alert('上传文件数量超出！');
            } else if ( type === 'Q_EXCEED_SIZE_LIMIT' ) {
                Alert('上传文件总大小超出！');
            } else if ( type === 'Q_TYPE_DENIED' ) {
                Alert('上传文件类型错误！');
            } else {
                alert( 'Error: ' + type );
            }
        } );
        
        
        
        // “上传”按钮 点击事件
        $uploadBtn.on('click', function() {
            
            if ( $(this).hasClass( 'disabled' ) ) {
                return false;
            }
            
            if ( oUpload._state === 'ready' ) {
                uploader.upload();
            } else if ( oUpload._state === 'paused' ) {
                uploader.upload();
            } else if ( oUpload._state === 'uploading' ) {
                uploader.stop();
            }
            
        } );
        
        
        
        $uploadBtn.addClass( 'state-' + oUpload._state );
        
        
        oUpload._uploader = uploader;
    };
    
    
    
    /**
     * 附件控件提交
     *  @param param {object} JSON数据
     *                  {
     *                      'success' : {function},
     *                      'error' : {function}
     *                  }
     */
    oUpload.submit = function(param) {
        oUpload._isSubmit = true;
        if ( param ) {
            if ( param.success ) {
                oUpload._submitSuccess = param.success;
            }
            if ( param.error ) {
                oUpload._submitError = param.error;
            }
        }
        
        // 判断是否有需要上传的文件
        if ( oUpload._state === 'pedding' ) {
            if ( $.isFunction(oUpload._submitSuccess) )
                oUpload._submitSuccess.call();
        } else if ( oUpload._state === 'ready' || oUpload._state === 'paused' ) {
            oUpload._uploader.upload();
        } else if ( oUpload._state === 'finish' ) {
            if ( $.isFunction(oUpload._submitSuccess) )
                oUpload._submitSuccess.call();
        }
    };
    
    
    oUpload.init();
    
    if (typeof(window.Sys_FileUploads) == 'undefined') {
        window.Sys_FileUploads = new Array();
    }
    window.Sys_FileUploads.push(oUpload);
    
    return oUpload;
    
};