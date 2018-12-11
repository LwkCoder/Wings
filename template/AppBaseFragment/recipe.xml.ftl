<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
	<#if includeLayout>
        <instantiate from="root/res/layout/app_fragment.xml.ftl"
                       to="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(fragmentName)}.xml" />

        <open file="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(fragmentName)}.xml" />
    </#if>

	<instantiate from="root/src/app_package/Contract.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/Contract.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/Contract.${ktOrJavaExt}" />
	
	<instantiate from="root/src/app_package/Model.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/Model.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/Model.${ktOrJavaExt}" />
	
	<instantiate from="root/src/app_package/Presenter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/Presenter.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/Presenter.${ktOrJavaExt}" />

    <instantiate from="root/src/app_package/AppFragment.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${className}.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${className}.${ktOrJavaExt}" />

</recipe>
