package net.wecash.common.bean;

import org.bson.types.ObjectId;

import java.util.ArrayList;


public class ResourceIdListRequestBody {
    private ArrayList<ObjectId> resourceIds;

    public ArrayList<ObjectId> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(ArrayList<ObjectId> resourceIds) {
        this.resourceIds = resourceIds;
    }

}
