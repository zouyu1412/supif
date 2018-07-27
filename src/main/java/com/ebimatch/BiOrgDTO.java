package com.ebimatch;

import java.util.List;

public class BiOrgDTO {

    private Long id;
    /**
     * 组织名称
     */
    private String name;
    /**
     * 简称
     */
    private String shortName;
    /**
     * 是否是叶子机构
     */
    private Boolean isLeaf;
    /**
     * 顺序号
     */
    private Integer order;
    /**
     * 数据来源 GEPS、BIM5D、CLOUDT
     */
    private String source;
    /**
     * 级别类型，
     * DL_GROUP：集团，
     * DL_CORP：公司，
     * DL_DEPARTMENT：部门
     * DL_PROJECT：项目部
     */
    private String nodeLevelType;
    /**
     * 下级机构，数组类型
     */
    private List<BiOrgDTO> childNodes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Boolean getLeaf() {
        return isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        isLeaf = leaf;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNodeLevelType() {
        return nodeLevelType;
    }

    public void setNodeLevelType(String nodeLevelType) {
        this.nodeLevelType = nodeLevelType;
    }

    public List<BiOrgDTO> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<BiOrgDTO> childNodes) {
        this.childNodes = childNodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BiOrgDTO biOrgDTO = (BiOrgDTO) o;

        if (!id.equals(biOrgDTO.id)) return false;
        if (name != null ? !name.equals(biOrgDTO.name) : biOrgDTO.name != null) return false;
        if (shortName != null ? !shortName.equals(biOrgDTO.shortName) : biOrgDTO.shortName != null) return false;
        if (isLeaf != null ? !isLeaf.equals(biOrgDTO.isLeaf) : biOrgDTO.isLeaf != null) return false;
        if (order != null ? !order.equals(biOrgDTO.order) : biOrgDTO.order != null) return false;
        if (source != null ? !source.equals(biOrgDTO.source) : biOrgDTO.source != null) return false;
        if (nodeLevelType != null ? !nodeLevelType.equals(biOrgDTO.nodeLevelType) : biOrgDTO.nodeLevelType != null)
            return false;
        return childNodes != null ? childNodes.equals(biOrgDTO.childNodes) : biOrgDTO.childNodes == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (isLeaf != null ? isLeaf.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (nodeLevelType != null ? nodeLevelType.hashCode() : 0);
        result = 31 * result + (childNodes != null ? childNodes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BiOrgDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", isLeaf=" + isLeaf +
                ", order=" + order +
                ", source='" + source + '\'' +
                ", nodeLevelType='" + nodeLevelType + '\'' +
                ", childNodes=" + childNodes +
                '}';
    }
}
