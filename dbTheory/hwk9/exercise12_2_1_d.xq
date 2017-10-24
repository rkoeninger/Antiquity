xquery version "1.0";
declare copy-namespaces no-preserve, inherit;

let $products := doc("products.xml")
for $maker in $products//Maker
let $acceptedPC := $maker/PC[data(//Speed) > 3.0]
where count($acceptedPC) >= 2
return <Maker name="{$maker/@name}"/>
