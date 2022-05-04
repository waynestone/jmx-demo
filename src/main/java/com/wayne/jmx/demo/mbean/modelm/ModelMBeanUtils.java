package com.wayne.jmx.demo.mbean.modelm;

import javax.management.Descriptor;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.modelmbean.*;

/**
 * Model MBean可以动态配置
 */
public class ModelMBeanUtils {
    private static final boolean READABLE = true;
    private static final boolean WRITABLE = true;
    private static final boolean BOOLEAN = true;
    private static final String STRING_CLASS = "java.lang.String";

    public static RequiredModelMBean createModelMBean() {
        RequiredModelMBean model = null;
        try {
            model = new RequiredModelMBean();
            //set Hello to ModelBean
            model.setManagedResource(new Hello(),"ObjectReference");

            //create mbean information
            ModelMBeanInfo info = createModelMBeanInfo();
            model.setModelMBeanInfo(info);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }


    private static ModelMBeanInfo createModelMBeanInfo(){
        //process exist attribute and method
        //create attribute of name
        Descriptor portAttrDesc = new DescriptorSupport();
        portAttrDesc.setField("name", "Name");
        portAttrDesc.setField("descriptorType", "attribute");
        portAttrDesc.setField("displayName", "Name");
        portAttrDesc.setField("getMethod", "getName");
        portAttrDesc.setField("setMethod", "setName");

        ModelMBeanAttributeInfo nameAttrInfo = new ModelMBeanAttributeInfo(//
                "Name", //attribute name
                STRING_CLASS, //attribute type
                "people name", //attribute desc
                READABLE, WRITABLE, !BOOLEAN,
                portAttrDesc //Descriptor
        );


        //getName
        Descriptor getStateDesc = new DescriptorSupport(new String[] {
                "name=getName", "descriptorType=operation", "class=com.wayne.jmx.demo.mbean.modelm.Hello",
                "role=operation" });

        ModelMBeanOperationInfo getName = new ModelMBeanOperationInfo(//
                "getName", //
                "get name attribute", //
                null, //
                "java.lang.String", //
                MBeanOperationInfo.ACTION, //
                getStateDesc //
        );


        //setName
        Descriptor setStateDesc = new DescriptorSupport(new String[] {
                "name=setName", "descriptorType=operation", "class=com.wayne.jmx.demo.mbean.modelm.Hello",
                "role=operation" });

        MBeanParameterInfo[] setStateParms = new MBeanParameterInfo[] { (new MBeanParameterInfo(
                "name", "java.lang.String", "new name value")) };

        ModelMBeanOperationInfo setName = new ModelMBeanOperationInfo(//
                "setName", //
                "set name attribute", //
                setStateParms, //
                "void", //
                MBeanOperationInfo.ACTION, //
                setStateDesc //
        );

        //create new method
        ModelMBeanOperationInfo print1Info = new ModelMBeanOperationInfo(
                "printHello",
                null,
                null,
                "void",
                MBeanOperationInfo.INFO,
                null);
        //create new method with params
        ModelMBeanOperationInfo print2Info;
        MBeanParameterInfo[] param2 = new MBeanParameterInfo[1];
        param2[0] = new MBeanParameterInfo("whoName", //param name
                STRING_CLASS, //param type
                "say hello to who");//param desc
        print2Info = new ModelMBeanOperationInfo(
                "printHello",
                null,
                param2,
                "void",
                MBeanOperationInfo.INFO,
                null);

        //create ModelMBeanInfo
        ModelMBeanInfo mbeanInfo = new ModelMBeanInfoSupport(//
                RequiredModelMBean.class.getName(), //MBean
                null, //desc
                new ModelMBeanAttributeInfo[] { //attributes
                        nameAttrInfo},
                null, //constructor
                new ModelMBeanOperationInfo[] { //operation
                        getName,
                        setName,
                        print1Info,
                        print2Info},
                null, //notification
                null);//desc
        return mbeanInfo;

    }



}
