package com.aws.talend.components.service;

import com.amazonaws.regions.Regions;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.api.record.Schema;
import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.completion.DynamicValues;
import org.talend.sdk.component.api.service.completion.Values;
import org.talend.sdk.component.api.service.schema.DiscoverSchema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class AwsSagemakerService {

    // you can put logic here you can reuse in components
    @DynamicValues("listAwsRegions")
    public Values regions() {
        Values values = new Values();
        List<Values.Item> items = new ArrayList<Values.Item>();

        for(Regions region : Regions.values())
        {
            items.add(new Values.Item(region.getName(), region.getName()));
        }

        values.setItems(items);
        return values;
    }

    @DynamicValues("listTypes")
    public Values types() {
        return new Values(Arrays.asList(new Values.Item("text/csv", "text/csv")));
    }


    public List<java.lang.String> processRecords(AwsSageMakerImpl awsSageMaker, List<Record> records) throws AwsSageMakerInvokeException
    {
        StringBuilder payload = new StringBuilder();
        List<java.lang.String> response = new ArrayList<java.lang.String>();
        for (Record record : records)
        {
            Schema schema = record.getSchema();
            List<Schema.Entry> entries = schema.getEntries();
            int size = entries.size();
            for (int i = 0; i < size; i++)
            {
                Schema.Entry entry = entries.get(i);
                java.lang.String value = null;
                switch (entry.getType())
                {
                    case DOUBLE:
                        value = new Double(record.getDouble(entry.getName())).toString();
                        break;
                    case STRING:
                        value = record.getString(entry.getName());
                        break;
                    case INT:
                        value =  new Integer(record.getInt(entry.getName())).toString();
                        break;
                    case LONG:
                        value =  new Long(record.getLong(entry.getName())).toString();
                        break;
                    case BYTES:
                        value =  new java.lang.String(record.getBytes(entry.getName()));
                        break;
                    case BOOLEAN:
                        value = new Boolean(record.getBoolean(entry.getName())).toString();
                        break;
                    case FLOAT:
                        value = new Float(record.getFloat(entry.getName())).toString();
                        break;
                    case DATETIME:
                        value = record.getDateTime(entry.getName()).toString();
                        break;
                }
                if (i == (size -1))
                {
                    payload.append(value+"\n");
                } else {
                    payload.append(value+",");
                }
            }
        }
        String resp = awsSageMaker.invokeEndpoint(payload.toString());
        String delimiter = ",";
        if (resp.startsWith("["))
            delimiter = "],";
        for (String rec : resp.split(delimiter))
        {
            if (!delimiter.equals(","))
                response.add(rec.replace("[",""));
            else
                response.add(rec);
        }
        return response;
    }
}