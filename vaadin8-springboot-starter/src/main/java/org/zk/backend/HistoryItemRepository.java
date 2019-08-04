package org.zk.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import org.zk.backend.data.entity.HistoryItem;

public interface HistoryItemRepository extends JpaRepository<HistoryItem, Long> {
}
