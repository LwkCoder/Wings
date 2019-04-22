<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
    
    <@kt.addAllKotlinDependencies />
	
	<merge from="root/AndroidManifest.xml.ftl"
             to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />

<#if generateLayout>
	<instantiate from="root/res/layout/list_activity.xml.ftl"
                       to="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
</#if>

	<instantiate from="root/src/app_package/Contract.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${activityClass}Contract.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${activityClass}Contract.${ktOrJavaExt}" />
	
	<instantiate from="root/src/app_package/Model.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${activityClass}Model.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${activityClass}Model.${ktOrJavaExt}" />
	
	<instantiate from="root/src/app_package/Presenter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${activityClass}Presenter.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${activityClass}Presenter.${ktOrJavaExt}" />
	
	<instantiate from="root/res/layout/list_adapter.xml.ftl"
                       to="${escapeXmlAttribute(resOut)}/layout/adapter_item_${activityClass?lower_case}.xml" />
    <open file="${escapeXmlAttribute(resOut)}/layout/adapter_item_${activityClass?lower_case}.xml" />
	
	<instantiate from="root/src/app_package/Adapter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${activityClass}Adapter.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${activityClass}Adapter.${ktOrJavaExt}" />

    <instantiate from="root/src/app_package/ListActivity.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${activityClass}Activity.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${activityClass}Activity.${ktOrJavaExt}" />

</recipe>
