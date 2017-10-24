xquery version "1.0";
declare copy-namespaces no-preserve, inherit;

<CheapPrinters>
{
let $products := doc("products.xml")
for $item in $products//Maker/Printer
where $item/@price < 100
return $item
}
</CheapPrinters>
