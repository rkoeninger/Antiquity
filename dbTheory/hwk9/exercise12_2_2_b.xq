xquery version "1.0";
declare copy-namespaces no-preserve, inherit;

let $document := doc("ships.xml")
for $class in $document//Ships/Class,
    $ship in $class/Ship
where ($class/@numGuns >= 10)
return <Ship name="{$ship/@name}"/>
