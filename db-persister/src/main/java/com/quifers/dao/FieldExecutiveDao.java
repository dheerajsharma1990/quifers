package com.quifers.dao;

import com.quifers.domain.FieldExecutive;

import java.util.Collection;

public interface FieldExecutiveDao {

    void saveFieldExecutive(FieldExecutive fieldExecutive);

    FieldExecutive getFieldExecutive(String fieldExecutiveId);

    Collection<FieldExecutive> getAllFieldExecutives();
}
