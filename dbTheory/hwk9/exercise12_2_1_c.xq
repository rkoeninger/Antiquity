xquery version "1.0";
declare copy-namespaces no-preserve, inherit;

let $products := doc("products.xml")
for $maker in $products//Maker
where count($maker/Printer) > 0 and count($maker/Laptop) > 0
return $maker
