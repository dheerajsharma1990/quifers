package com.quifers.dao;

import com.quifers.domain.FieldExecutive;
import com.quifers.domain.id.FieldExecutiveId;

import java.util.Collection;

public interface FieldExecutiveDao {

    void saveFieldExecutive(FieldExecutive fieldExecutive);

    FieldExecutive getFieldExecutive(FieldExecutiveId fieldExecutiveId);

    Collection<FieldExecutive> getAllFieldExecutives();
}
