package org.apache.dolphinscheduler.api.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TreeUtils<T, P> {

    public List<TreeNode<T, P>> buildTree(List<T> items, String idField, String parentIdField, P topParentId) {
        Map<P, TreeNode<T, P>> itemMap = new HashMap<>();

        // Convert list items to tree nodes and store in map for quick lookup
        for (T item : items) {
            TreeNode<T, P> node = createNode(item, idField, parentIdField);
            itemMap.put(getField(item, idField), node); // Use node's own ID for map key
        }

        // Identify top nodes
        List<TreeNode<T, P>> topNodes = itemMap.values().stream()
                .filter(node -> node.getParentId().equals(topParentId))
                .collect(Collectors.toList());

        // Link children to their parents
        for (TreeNode<T, P> node : itemMap.values()) {
            P parentId = node.getParentId();
            if (!parentId.equals(topParentId) && itemMap.containsKey(parentId)) {
                itemMap.get(parentId).addChild(node);
            }
        }

        return topNodes;
    }

    private TreeNode<T, P> createNode(T item, String idField, String parentIdField) {
        P id = getField(item, idField);
        P parentId = getField(item, parentIdField);
        return new TreeNode<>(item, parentId);
    }

    private P getField(T item, String fieldName) {
        try {
            Field field = item.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (P) field.get(item);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error accessing field: " + fieldName, e);
        }
    }
}
