# TODO App

シンプルなTODOアプリケーションです。Nuxt3フロントエンドとSpring Boot APIバックエンドで構成されています。

## 機能

- ✅ TODOの追加（タイトル・詳細）
- ✅ TODOの完了/未完了切り替え
- ✅ TODOの編集（インライン編集、タイトル・詳細両方）
- ✅ TODOの削除
- ✅ 詳細説明の表示・編集
- ✅ レスポンシブデザイン（スマートフォン対応）

## 技術スタック

### フロントエンド
- **Nuxt3** - Vue.jsベースのフルスタックフレームワーク
- **TailwindCSS** - ユーティリティファーストCSSフレームワーク
- **TypeScript** - 型安全なJavaScript

### バックエンド
- **Spring Boot 3.2.0** - Javaベースのアプリケーションフレームワーク
- **Spring Data JPA** - データアクセス層
- **H2 Database** - インメモリデータベース（開発用）
- **Maven** - 依存関係管理・ビルドツール

## プロジェクト構成

```
claude-todo/
├── README.md
├── backend/           # Spring Boot API
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/example/todoapi/
│           │       ├── TodoApiApplication.java
│           │       ├── controller/
│           │       │   └── TodoController.java
│           │       ├── entity/
│           │       │   └── Todo.java
│           │       └── repository/
│           │           └── TodoRepository.java
│           └── resources/
│               └── application.yml
└── frontend/          # Nuxt3 アプリ
    ├── nuxt.config.ts
    ├── package.json
    ├── app/
    │   └── app.vue
    └── types/
        └── todo.ts
```

## セットアップ・実行方法

### 必要な環境
- Java 17以上
- Node.js 18以上
- Maven 3.6以上

### 1. プロジェクトのクローン
```bash
git clone <repository-url>
cd claude-todo
```

### 2. バックエンド（Spring Boot API）の起動
```bash
cd backend
mvn spring-boot:run
```
→ http://localhost:8081 でAPIサーバーが起動します

### 3. フロントエンド（Nuxt3）の起動
新しいターミナルで：
```bash
cd frontend
npm install
npm run dev
```
→ http://localhost:3000 でWebアプリが起動します

## API仕様

### エンドポイント

| メソッド | URL | 説明 |
|---------|-----|------|
| GET | `/api/todos` | TODO一覧取得 |
| GET | `/api/todos/{id}` | 指定ID のTODO取得 |
| POST | `/api/todos` | TODO作成 |
| PUT | `/api/todos/{id}` | TODO更新 |
| DELETE | `/api/todos/{id}` | TODO削除 |

### TODOエンティティ

```json
{
  "id": 1,
  "title": "買い物に行く",
  "description": "牛乳、パン、卵を購入する",
  "completed": false,
  "createdAt": "2025-08-16T09:00:00"
}
```

## 使用方法

1. **TODOの追加**
   - 上部のタイトル入力フィールドにタスクタイトルを入力
   - 詳細フィールドに説明を入力（任意）
   - 「Add Todo」ボタンをクリックまたはEnterキーを押下

2. **TODOの完了/未完了切り替え**
   - 各TODOの左側にあるチェックボックスをクリック

3. **TODOの編集**
   - 各TODOの右側にある編集ボタン（✏️）をクリック
   - インライン編集でタイトルと詳細を変更
   - 「Save」ボタンで保存、「Cancel」ボタンでキャンセル

4. **TODOの削除**
   - 各TODOの右側にある削除ボタン（🗑️）をクリック

## 開発者向け情報

### CORS設定
フロントエンド（localhost:3000）からバックエンド（localhost:8081）へのアクセスを許可するようCORS設定済み

### データベース
開発用にH2インメモリデータベースを使用。アプリケーション再起動時にデータはリセットされます。

### H2コンソール
開発中はH2コンソールにアクセス可能：
- URL: http://localhost:8081/h2-console
- JDBC URL: `jdbc:h2:mem:tododb`
- Username: `sa`
- Password: `password`

## ライセンス

このプロジェクトはMITライセンスの下で公開されています。

## 貢献

プルリクエストやイシューの報告を歓迎します！

---

**開発者:** Claude Code Assistant
**作成日:** 2025年8月16日