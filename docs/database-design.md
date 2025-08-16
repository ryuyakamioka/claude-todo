# 🗄 データベース設計書

## 概要

Cat Todo アプリケーションのデータベーススキーマ設計と仕様

## データベース情報

### 開発環境
- **DB種別**: H2 Database (インメモリ)
- **接続URL**: `jdbc:h2:mem:tododb`
- **ユーザー**: `sa`
- **パスワード**: `password`

### 本番環境（推奨）
- **DB種別**: PostgreSQL / MySQL
- **文字コード**: UTF-8
- **タイムゾーン**: UTC

## テーブル設計

### todos テーブル

TODOアイテムの情報を格納するメインテーブル

```sql
CREATE TABLE todos (
    id              BIGINT          AUTO_INCREMENT PRIMARY KEY,
    title           VARCHAR(255)    NOT NULL,
    description     TEXT,
    completed       BOOLEAN         NOT NULL DEFAULT FALSE,
    due_date        TIMESTAMP,
    display_order   INT             NOT NULL DEFAULT 0,
    created_at      TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

#### カラム仕様

| カラム名 | データ型 | 制約 | デフォルト | 説明 |
|----------|----------|------|------------|------|
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | - | 自動採番ID |
| `title` | VARCHAR(255) | NOT NULL | - | TODOタイトル |
| `description` | TEXT | NULL許可 | - | 詳細説明 |
| `completed` | BOOLEAN | NOT NULL | FALSE | 完了フラグ |
| `due_date` | TIMESTAMP | NULL許可 | - | 期限日時 |
| `display_order` | INT | NOT NULL | 0 | 表示順序 |
| `created_at` | TIMESTAMP | NOT NULL | CURRENT_TIMESTAMP | 作成日時 |

#### インデックス

```sql
-- 表示順ソート用（頻繁に使用）
CREATE INDEX idx_display_order ON todos(display_order);

-- 作成日時ソート用
CREATE INDEX idx_created_at ON todos(created_at);

-- 期限検索用
CREATE INDEX idx_due_date ON todos(due_date);

-- 完了状態フィルタ用
CREATE INDEX idx_completed ON todos(completed);
```

## データ制約

### ビジネスルール制約

#### アプリケーションレベル
- `title`: 1文字以上255文字以下（必須）
- `description`: 最大65,535文字（TEXT型制限）
- `completed`: true/false のみ
- `display_order`: 0以上の整数
- `due_date`: 現在時刻以降推奨（警告表示）

#### データベースレベル
```sql
-- タイトル長制約
ALTER TABLE todos ADD CONSTRAINT chk_title_length 
CHECK (LENGTH(title) >= 1 AND LENGTH(title) <= 255);

-- 表示順序制約
ALTER TABLE todos ADD CONSTRAINT chk_display_order_positive 
CHECK (display_order >= 0);
```

## サンプルデータ

### 初期データ例

```sql
INSERT INTO todos (title, description, completed, due_date, display_order) VALUES
('猫のご飯を買う', 'プレミアムドライフードを購入する', false, '2025-08-23 23:59:59', 0),
('獣医さんの予約', '年1回の健康診断', false, '2025-08-30 23:59:59', 1),
('爪とぎポール交換', '古くなったので新しいものに', true, '2025-08-20 23:59:59', 2);
```

## クエリパターン

### 基本的なCRUD操作

#### 1. 全件取得（表示順ソート）
```sql
SELECT * FROM todos 
ORDER BY display_order ASC;
```

#### 2. ID指定取得
```sql
SELECT * FROM todos 
WHERE id = ?;
```

#### 3. 新規作成
```sql
INSERT INTO todos (title, description, due_date, display_order) 
VALUES (?, ?, ?, (SELECT COALESCE(MAX(display_order), 0) + 1 FROM todos));
```

#### 4. 更新
```sql
UPDATE todos 
SET title = ?, description = ?, completed = ?, due_date = ? 
WHERE id = ?;
```

#### 5. 削除
```sql
DELETE FROM todos 
WHERE id = ?;
```

### 高度なクエリ

#### 期限切れTODO検索
```sql
SELECT * FROM todos 
WHERE due_date < CURRENT_TIMESTAMP 
  AND completed = false
ORDER BY due_date ASC;
```

#### 表示順一括更新
```sql
-- JPA バッチ更新を使用
-- 複数のUPDATE文を実行
UPDATE todos SET display_order = ? WHERE id = ?;
```

#### 統計情報取得
```sql
SELECT 
    COUNT(*) as total_todos,
    SUM(CASE WHEN completed = true THEN 1 ELSE 0 END) as completed_todos,
    SUM(CASE WHEN due_date < CURRENT_TIMESTAMP AND completed = false THEN 1 ELSE 0 END) as overdue_todos
FROM todos;
```

## パフォーマンス考慮事項

### インデックス戦略
1. **display_order**: 一覧表示で常にソートに使用
2. **created_at**: 作成日時でのソート・フィルタ
3. **due_date**: 期限検索・期限切れ判定
4. **completed**: 完了・未完了フィルタ

### クエリ最適化
- `ORDER BY display_order` は専用インデックス使用
- 期限切れ検索は `due_date` インデックス使用
- バッチ更新でN+1問題回避

## マイグレーション

### バージョン管理

#### V1.0.0: 初期スキーマ
```sql
CREATE TABLE todos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

#### V1.1.0: 詳細説明追加
```sql
ALTER TABLE todos ADD COLUMN description TEXT;
```

#### V1.2.0: 期限機能追加
```sql
ALTER TABLE todos ADD COLUMN due_date TIMESTAMP;
CREATE INDEX idx_due_date ON todos(due_date);
```

#### V1.3.0: 表示順機能追加
```sql
ALTER TABLE todos ADD COLUMN display_order INT NOT NULL DEFAULT 0;
CREATE INDEX idx_display_order ON todos(display_order);

-- 既存データの display_order を id 順で設定
UPDATE todos SET display_order = id;
```

## 運用上の考慮事項

### データ保持期間
- **完了済みTODO**: 無期限保持（ユーザー判断で削除）
- **論理削除**: 現在は物理削除（将来的に論理削除検討）

### バックアップ戦略
- **開発環境**: インメモリのため不要
- **本番環境**: 日次バックアップ推奨

### セキュリティ
- **SQLインジェクション**: JPA/Hibernate の Prepared Statement で対策
- **データ暗号化**: 現在は平文（本番では暗号化検討）

## トラブルシューティング

### よくある問題

#### 1. 表示順重複
```sql
-- 重複チェック
SELECT display_order, COUNT(*) 
FROM todos 
GROUP BY display_order 
HAVING COUNT(*) > 1;

-- 修正（再採番）
SET @row_number = 0;
UPDATE todos 
SET display_order = (@row_number := @row_number + 1) 
ORDER BY id;
```

#### 2. 孤立した表示順
```sql
-- 連番に修正
SET @row_number = 0;
UPDATE todos 
SET display_order = (@row_number := @row_number + 1) 
ORDER BY display_order, id;
```

## 将来の拡張案

### 追加予定フィールド
- `priority`: 優先度（HIGH, MEDIUM, LOW）
- `category_id`: カテゴリ分類
- `user_id`: マルチユーザー対応
- `updated_at`: 更新日時
- `deleted_at`: 論理削除フラグ

### 追加予定テーブル
- `categories`: TODOカテゴリ
- `users`: ユーザー情報
- `todo_attachments`: 添付ファイル