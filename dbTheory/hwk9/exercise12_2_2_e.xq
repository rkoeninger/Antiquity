xquery version "1.0";
declare copy-namespaces no-preserve, inherit;

let $document := doc("ships.xml")
for $class in $document//Ships/Class
where every $b in $class/Ship/Battle satisfies $b/@outcome != "sunk"
return <Class name="{$class/@name}"/>
