xquery version "1.0";
declare copy-namespaces no-preserve, inherit;

let $document := doc("medline10n0770.xml")
for $citation in $document//MedlineCitationSet/MedlineCitation
where (data($citation/Article/ArticleDate/Year) = "2010") and (data($citation/Article/ArticleDate/Month) = "05")
return <MedlineCitation pmid="{$citation/PMID}"/>
