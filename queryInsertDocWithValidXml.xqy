xquery version "1.0-ml";
declare namespace html = "http://www.w3.org/1999/xhtml";

import schema "http://www.example.org/uri" at "/D/urisschema/uri.xsd";

(: 
this will validate against the specified schema, and will fail
if the schema does not exist (or if it is not valid according to
the schema)
:)

for $n in (33 to 55)

let $node := xdmp:unquote(fn:concat('<?xml version="1.0" encoding="UTF-8"?>
<tns:simpleuri xmlns:tns="http://www.example.org/uri" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.example.org/uri uri.xsd ">',fn:codepoints-to-string($n), '.org
</tns:simpleuri>'))

return
try { 
xdmp:document-insert(fn:concat("/my-valid-uri-document<",$n,"-", fn:codepoints-to-string($n) ,".xml"), 
validate strict { $node } ) 
}
catch ($e)
{
fn:concat($n, ":Validation failed: ", $e/error:message/text())

}
