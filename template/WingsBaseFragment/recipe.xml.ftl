<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
	<#if generateLayout>
        <instantiate from="root/res/layout/app_fragment.xml.ftl"
                       to="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(layoutName)}.xml" />

        <open file="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(layoutName)}.xml" />
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

    <instantiate from="root/src/app_package/AppFragment.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${fragmentClass}Fragment.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${fragmentClass}Fragment.${ktOrJavaExt}" />

</recipe>
