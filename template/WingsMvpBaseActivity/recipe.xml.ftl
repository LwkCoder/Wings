<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
    
    <@kt.addAllKotlinDependencies />
	
	<merge from="root/AndroidManifest.xml.ftl"
             to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />

<#if generateLayout>
	<instantiate from="root/res/layout/layout.xml.ftl"
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

    <instantiate from="root/src/app_package/Activity.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${uiClassName}Activity.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${uiClassName}Activity.${ktOrJavaExt}" />

</recipe>
