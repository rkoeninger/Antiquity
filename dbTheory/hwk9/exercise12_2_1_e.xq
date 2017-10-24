xquery version "1.0";
declare copy-namespaces no-preserve, inherit;

let $products := doc("products.xml")
for $maker in $products//Maker
let $acceptedPC := $maker/PC[@price <= 1000]
where (count($acceptedPC) = count($maker/PC)) and (count($maker/PC) > 0)
return <Maker name="{$maker/@name}"/>
