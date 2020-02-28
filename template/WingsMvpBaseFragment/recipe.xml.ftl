<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
	<#if generateLayout>
        <instantiate from="root/res/layout/layout.xml.ftl"
                       to="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(layoutName)}.xml" />

        <open file="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(layoutName)}.xml" />
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

</recipe>
