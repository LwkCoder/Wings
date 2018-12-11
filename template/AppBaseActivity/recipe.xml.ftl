<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
    <#include "../common/recipe_manifest.xml.ftl" />
    <@kt.addAllKotlinDependencies />

<#if generateLayout>
	<instantiate from="root/res/layout/app_activity.xml.ftl"
                       to="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
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

    <instantiate from="root/src/app_package/AppActivity.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${activityClass}.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${activityClass}.${ktOrJavaExt}" />

</recipe>
