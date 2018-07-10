package benny.dev.tasktimer;

import java.io.Serializable;

class Task implements Serializable{
    public static final long serialVersionUID = 20161120L; // to prevent different version

    // fragment is a subactvitity. It can be embedded in another activity to have its own function. You can reuse it with different activities

    // just need a field to store each value from database
    private long m_Id;
    private final String mName;
    private final String mDescription;
    private final int mSortOrder;

    public Task(long id, String name, String description, int sortOrder) {
        this.m_Id = id;
        mName = name;
        mDescription = description;
        mSortOrder = sortOrder;
    }

    public long getId() {
        return m_Id;
    }

    // need setter for id as we may insert new field


    public void setId(long id) {
        this.m_Id = id;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getSortOrder() {
        return mSortOrder;
    }

    @Override
    public String toString() {
        return "Task{" +
                "m_Id=" + m_Id +
                ", mName='" + mName + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mSortOrder=" + mSortOrder +
                '}';
    }
}
