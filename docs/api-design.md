# 📡 API設計書

## 概要

Cat Todo APIは RESTful な設計に基づき、TODOアイテムの管理機能を提供します。

## ベースURL

```
開発環境: http://localhost:8081/api
```

## 認証

現在は認証なし（開発版）

## エンドポイント一覧

| Method | Endpoint | 説明 | 実装済み |
|--------|----------|------|----------|
| GET | `/todos` | TODO一覧取得（表示順ソート） | ✅ |
| GET | `/todos/{id}` | 指定IDのTODO取得 | ✅ |
| POST | `/todos` | 新規TODO作成 | ✅ |
| PUT | `/todos/{id}` | TODO更新 | ✅ |
| DELETE | `/todos/{id}` | TODO削除 | ✅ |
| PUT | `/todos/reorder` | TODO順序一括更新 | ✅ |

## データモデル

### Todo Entity
```json
{
  "id": 1,
  "title": "猫のご飯を買う",
  "description": "プレミアムドライフードを購入",
  "completed": false,
  "dueDate": "2025-08-23T23:59:59",
  "displayOrder": 0,
  "createdAt": "2025-08-16T10:00:00"
}
```

### フィールド説明
| フィールド | 型 | 必須 | 説明 |
|------------|----|----|------|
| `id` | Long | - | 自動生成ID |
| `title` | String | ✅ | TODOタイトル（最大255文字） |
| `description` | String | - | 詳細説明（TEXT型） |
| `completed` | Boolean | ✅ | 完了状態（デフォルト: false） |
| `dueDate` | LocalDateTime | - | 期限日時 |
| `displayOrder` | Integer | ✅ | 表示順序（デフォルト: 0） |
| `createdAt` | LocalDateTime | ✅ | 作成日時（自動設定） |

## API詳細仕様

### 1. TODO一覧取得

```http
GET /api/todos
```

**レスポンス例:**
```json
[
  {
    "id": 1,
    "title": "猫のご飯を買う",
    "description": "プレミアムドライフード",
    "completed": false,
    "dueDate": "2025-08-23T23:59:59",
    "displayOrder": 0,
    "createdAt": "2025-08-16T10:00:00"
  }
]
```

### 2. TODO作成

```http
POST /api/todos
Content-Type: application/json
```

**リクエストボディ:**
```json
{
  "title": "新しいタスク",
  "description": "タスクの詳細",
  "dueDate": "2025-08-30T23:59:59"
}
```

**レスポンス:** 作成されたTODOオブジェクト

### 3. TODO更新

```http
PUT /api/todos/{id}
Content-Type: application/json
```

**リクエストボディ例:**
```json
{
  "title": "更新されたタイトル",
  "completed": true,
  "dueDate": "2025-08-25T23:59:59"
}
```

### 4. TODO削除

```http
DELETE /api/todos/{id}
```

**レスポンス:** 200 OK または 404 Not Found

### 5. TODO順序更新

```http
PUT /api/todos/reorder
Content-Type: application/json
```

**リクエストボディ:**
```json
[2, 1, 3]
```
*TODO IDの新しい順序配列*

## エラーレスポンス

### 400 Bad Request
```json
{
  "timestamp": "2025-08-16T10:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/todos"
}
```

### 404 Not Found
```json
{
  "timestamp": "2025-08-16T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Todo not found with id: 999",
  "path": "/api/todos/999"
}
```

## CORS設定

```java
@CrossOrigin(origins = "http://localhost:3000")
```

## Swagger UI

開発時のAPI仕様確認・テスト:
```
http://localhost:8081/swagger-ui.html
```

OpenAPI JSON仕様書:
```
http://localhost:8081/v3/api-docs
```

## データベーススキーマ

```sql
CREATE TABLE todos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    due_date TIMESTAMP,
    display_order INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_display_order ON todos(display_order);
```

## 実装上の注意点

1. **displayOrder**: 新規作成時は自動で最後尾に設定
2. **dueDate**: フロントエンドからは日付のみ受信、23:59:59で保存
3. **バリデーション**: title は必須、null チェック実装済み
4. **ソート**: 常に displayOrder 昇順で返却