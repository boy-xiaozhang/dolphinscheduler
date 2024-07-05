package org.apache.dolphinscheduler.api.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class TreeNode<T, P> {
    private T node;
    private P parentId;
    private List<TreeNode<T, P>> children;

    public TreeNode(T data, P parentId) {
        this.node = data;
        this.parentId = parentId;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode<T, P> child) {
        children.add(child);
    }
}
