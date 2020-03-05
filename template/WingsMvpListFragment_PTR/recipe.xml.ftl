<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
    
    <@kt.addAllKotlinDependencies />
	
	<#if generateLayout>
	<instantiate from="root/res/layout/list_layout.xml.ftl"
                       to="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
	</#if>

	<instantiate from="root/src/app_package/Contract.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${uiClassName}Contract.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${uiClassName}Contract.${ktOrJavaExt}" />
	
	<instantiate from="root/src/app_package/Model.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${uiClassName}Model.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${uiClassName}Model.${ktOrJavaExt}" />
	
	<instantiate from="root/src/app_package/Presenter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${uiClassName}Presenter.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${uiClassName}Presenter.${ktOrJavaExt}" />

    <instantiate from="root/src/app_package/Fragment.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${uiClassName}Fragment.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${uiClassName}Fragment.${ktOrJavaExt}" />
	
	<instantiate from="root/src/app_package/Adapter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${uiClassName}Adapter.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${uiClassName}Adapter.${ktOrJavaExt}" />
	
	<instantiate from="root/res/layout/adapter_layout.xml.ftl"
                       to="${escapeXmlAttribute(resOut)}/layout/adapter_item_${uiClassName?lower_case}.xml" />
    <open file="${escapeXmlAttribute(resOut)}/layout/adapter_item_${uiClassName?lower_case}.xml" />

</recipe>
