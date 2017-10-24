xquery version "1.0";
declare copy-namespaces no-preserve, inherit;

let $document := doc("ships.xml")
for $class in $document//Ships/Class
where $class/@numGuns >= 10
return <Class name="{$class/@name}"/>
