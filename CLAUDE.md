# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## プロジェクト概要

Cat Todoは、Nuxt3フロントエンドとSpring Bootバックエンドで構成されたフルスタックTODOアプリケーションです。フロントエンドはポート3000、バックエンドはポート8081で動作し、APIエンドポイントには`/api`プレフィックスが付きます。

## 開発コマンド

### フロントエンド（Nuxt3）
```bash
cd frontend
npm install          # 依存関係のインストール
npm run dev          # 開発サーバー起動 (http://localhost:3000)
npm run build        # 本番用ビルド
npm run generate     # 静的サイト生成
npm run preview      # 本番ビルドのプレビュー
```

### バックエンド（Spring Boot）
```bash
cd backend
mvn spring-boot:run  # 開発サーバー起動 (http://localhost:8081)
mvn clean install   # ビルドとテスト実行
mvn test            # テストのみ実行
```

## アーキテクチャ

### フロントエンド構造
- **コンポーネント**: `frontend/components/` - TodoApp、TodoForm、TodoList、TodoItemなどのVue.jsコンポーネント
- **コンポーザブル**: `frontend/composables/useTodos.ts` - TODOの状態管理とAPI呼び出しを一元化
- **型定義**: `frontend/types/todo.ts` - TodoエンティティのTypeScriptインターフェース
- **ページ**: `frontend/pages/` - Nuxt.jsのルーティング用ページ

### バックエンド構造
- **コントローラー**: `TodoController.java` - Swaggerドキュメント付きREST APIエンドポイント
- **エンティティ**: `Todo.java` - id、title、description、completed、createdAt、dueDate、displayOrderフィールドを持つJPAエンティティ
- **リポジトリ**: `TodoRepository.java` - データベース操作用のSpring Data JPAリポジトリ

### 主要機能
- displayOrderフィールドによるドラッグ&ドロップ並び替え
- 期限日機能
- タイトルと詳細のインライン編集
- localhost:3000 → localhost:8081のCORS設定

### APIエンドポイント
- `GET /api/todos` - 全TODO取得（displayOrder順でソート）
- `GET /api/todos/{id}` - 特定TODO取得
- `POST /api/todos` - 新規TODO作成
- `PUT /api/todos/{id}` - 既存TODO更新
- `DELETE /api/todos/{id}` - TODO削除
- `PUT /api/todos/reorder` - TODO一括並び替え

### データベース
- 開発用H2インメモリデータベース
- H2コンソール: http://localhost:8081/h2-console
- JDBC URL: `jdbc:h2:mem:tododb`、ユーザー名: `sa`、パスワード: `password`

### APIドキュメント
- Swagger UI: http://localhost:8081/swagger-ui.html
- OpenAPI仕様: http://localhost:8081/v3/api-docs

## 開発ワークフロー

1. バックエンドを先に起動: `cd backend && mvn spring-boot:run`
2. フロントエンドを起動: `cd frontend && npm run dev`
3. 完全な機能のために両方のサービスが動作している必要があります
4. フロントエンドは`nuxt.config.ts`で設定された`apiBase`経由でバックエンドと通信

## 重要な注意点

- `useTodos`コンポーザブルがすべてのAPI相互作用と状態管理を処理
- Todoエンティティにはドラッグ&ドロップ機能用の`displayOrder`が含まれる
- すべてのAPI呼び出しはNuxtの`$fetch`ユーティリティを使用
- TodoControllerでフロントエンドアクセス用のCORSが設定済み