package com.fiveware.model;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "bot")
public class Bot implements Serializable {

    public static final int DESCRIPTION_LENGTH = 120;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_bot")
    private String nameBot;

    @Column(name = "method")
    private String method;

    @Column(name = "endpoint")
    private String endpoint;

    @Column(name ="fields_input")
    private String fieldsInput;

    @Column(name = "fields_output")
    private String fieldsOutput;

    @Column(name = "type_file_in")
    private String typeFileIn;

    @Column(name = "separator_file")
    private String separatorFile;

    @Column(name = "version")
    private String version;

    @Column(name = "classloader")
    private String classloader;

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "bot_has_inputFields", joinColumns =
            {@JoinColumn(name = "bot_id")}, inverseJoinColumns =
            {@JoinColumn(name = "inputField_id")})
    private Set<InputField> inputFields;

    public Bot() {
        this.inputFields = Sets.newHashSet();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameBot() {
        return nameBot;
    }

    public void setNameBot(String nameBot) {
        this.nameBot = nameBot;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getFieldsInput() {
        return fieldsInput;
    }

    public void setFieldsInput(String fieldsInput) {
        this.fieldsInput = fieldsInput;
    }

    public String getFieldsOutput() {
        return fieldsOutput;
    }

    public void setFieldsOutput(String fieldsOutput) {
        this.fieldsOutput = fieldsOutput;
    }

    public String getTypeFileIn() {
        return typeFileIn;
    }

    public void setTypeFileIn(String typeFileIn) {
        this.typeFileIn = typeFileIn;
    }

    public String getSeparatorFile() {
        return separatorFile;
    }

    public void setSeparatorFile(String separatorFile) {
        this.separatorFile = separatorFile;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getClassloader() {
        return classloader;
    }

    public void setClassloader(String classloader) {
        this.classloader = classloader;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setInputFields(Set<InputField> inputFields) {
        this.inputFields = inputFields;
    }

    public void addField(InputField inputField) {
        if (!this.inputFields.contains(inputField))
            this.inputFields.add(inputField);
    }

    public int totalInputFields() {
        return this.inputFields.size();
    }
}