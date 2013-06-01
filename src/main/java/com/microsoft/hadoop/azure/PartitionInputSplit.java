package com.microsoft.hadoop.azure;

import java.io.*;

import org.apache.hadoop.io.*;

import com.microsoft.windowsazure.services.table.client.TableQuery;

public class PartitionInputSplit extends AzureTableInputSplit {
	private String partitionKey;
	
	PartitionInputSplit() {}
	
	public PartitionInputSplit(String partitionKey) {
		this.partitionKey = partitionKey;
	}
	
	public String getPartitionKey() {
		return partitionKey;
	}
	
	@Override
	public void write(DataOutput out) throws IOException {
		Text.writeString(out, partitionKey);
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		partitionKey = Text.readString(in);
	}

	@Override
	public TableQuery<WritableEntity> getQuery(String tableName) {
		return TableQuery
				.from(tableName, WritableEntity.class)
				.where("PartitionKey eq '" + partitionKey + "'");
	}
}
