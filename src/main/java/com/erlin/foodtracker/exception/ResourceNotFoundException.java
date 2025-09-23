package com.erlin.foodtracker.exception;

public class ResourceNotFoundException extends RuntimeException {
   String resourceName;
   String fieldName;
   String fieldValue;
   Long fieldId;

   public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
       super(String.format("Resource %s not found with name %s and value %s" ,  resourceName , fieldName , fieldValue));
       this.resourceName = resourceName;
       this.fieldName = fieldName;
       this.fieldValue = fieldValue;
   }

    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldId) {
        super(String.format("Resource %s not found with name %s and id: %d" ,  resourceName , fieldName , fieldId));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldId = fieldId;
    }
}
