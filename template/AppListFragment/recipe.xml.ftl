<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>

<#if generateLayout>
	<instantiate from="root/res/layout/list_fragment.xml.ftl"
                       to="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
</#if>

	<instantiate from="root/src/app_package/Contract.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${fragmentClass}Contract.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${fragmentClass}Contract.${ktOrJavaExt}" />
	
	<instantiate from="root/src/app_package/Model.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${fragmentClass}Model.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${fragmentClass}Model.${ktOrJavaExt}" />
	
	<instantiate from="root/src/app_package/Presenter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${fragmentClass}Presenter.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${fragmentClass}Presenter.${ktOrJavaExt}" />
	
	<instantiate from="root/res/layout/list_adapter.xml.ftl"
                       to="${escapeXmlAttribute(resOut)}/layout/adapter_item_${fragmentClass?lower_case}.xml" />
    <open file="${escapeXmlAttribute(resOut)}/layout/adapter_item_${fragmentClass?lower_case}.xml" />
	
	<instantiate from="root/src/app_package/Adapter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${fragmentClass}Adapter.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${fragmentClass}Adapter.${ktOrJavaExt}" />

    <instantiate from="root/src/app_package/ListFragment.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${fragmentClass}Fragment.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${fragmentClass}Fragment.${ktOrJavaExt}" />

</recipe>
