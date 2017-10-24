xquery version "1.0";
declare copy-namespaces no-preserve, inherit;

let $document := doc("ships.xml")
for $ship in $document//Ships/Class/Ship[./Battle/@outcome = "sunk"]
return <Ship name="{$ship/@name}"/>
