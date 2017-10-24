xquery version "1.0";
declare copy-namespaces no-preserve, inherit;

let $document := doc("ships.xml")
for $class in $document//Ships/Class
where count($class/Ship) >= 3
return <Class name="{$class/@name}"/>
