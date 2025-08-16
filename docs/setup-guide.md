# 🚀 開発環境セットアップガイド

## 必要な環境

### システム要件
- **Java**: 17以上（LTS推奨）
- **Node.js**: 18以上
- **Maven**: 3.6以上
- **Git**: 最新版

### 推奨開発環境
- **IDE**: IntelliJ IDEA（バックエンド）/ VS Code（フロントエンド）
- **ブラウザ**: Chrome, Firefox（開発者ツール使用）
- **OS**: Windows/macOS/Linux

## セットアップ手順

### 1. プロジェクトのクローン

```bash
git clone <repository-url>
cd claude-todo
```

### 2. プロジェクト構造確認

```
claude-todo/
├── README.md              # プロジェクト概要
├── docs/                  # ドキュメント
├── backend/               # Spring Boot API
│   ├── pom.xml
│   └── src/main/java/
└── frontend/              # Nuxt3 アプリ
    ├── package.json
    ├── app/app.vue
    └── types/todo.ts
```

### 3. バックエンド（Spring Boot）セットアップ

```bash
# バックエンドディレクトリに移動
cd backend

# 依存関係のダウンロード（初回のみ）
mvn clean install

# アプリケーション起動
mvn spring-boot:run
```

**起動確認:**
- API サーバー: http://localhost:8081
- Swagger UI: http://localhost:8081/swagger-ui.html
- H2 Console: http://localhost:8081/h2-console

### 4. フロントエンド（Nuxt3）セットアップ

```bash
# 新しいターミナルでフロントエンドディレクトリに移動
cd frontend

# 依存関係のインストール
npm install

# 開発サーバー起動
npm run dev
```

**起動確認:**
- Web アプリ: http://localhost:3000

## 設定情報

### ポート設定
| サービス | ポート | URL |
|----------|--------|-----|
| Frontend | 3000 | http://localhost:3000 |
| Backend API | 8081 | http://localhost:8081/api |
| Swagger UI | 8081 | http://localhost:8081/swagger-ui.html |
| H2 Console | 8081 | http://localhost:8081/h2-console |

### H2 データベース接続情報
```
JDBC URL: jdbc:h2:mem:tododb
Username: sa
Password: password
Driver Class: org.h2.Driver
```

### CORS設定
- Frontend (localhost:3000) ↔ Backend (localhost:8081)
- 開発環境用設定済み

## トラブルシューティング

### よくある問題と解決方法

#### 1. ポート競合エラー
```
Error: Port 8080 already in use
```
**解決方法:**
- バックエンドは8081ポートを使用（設定済み）
- フロントエンドで3000ポートが使用中の場合、別ポートを指定

#### 2. Java バージョンエラー
```
Error: Unsupported Java version
```
**解決方法:**
```bash
# Java バージョン確認
java -version

# Java 17 以上であることを確認
# 必要に応じてJDKをアップデート
```

#### 3. Node.js バージョンエラー
```
Error: Node.js version not supported
```
**解決方法:**
```bash
# Node.js バージョン確認
node --version

# 18以上であることを確認
# nvm使用推奨：nvm install 18
```

#### 4. 依存関係エラー
```
Error: Cannot resolve dependencies
```
**解決方法:**
```bash
# フロントエンド
cd frontend
rm -rf node_modules package-lock.json
npm install

# バックエンド
cd backend
mvn clean install
```

#### 5. CORS エラー
```
Access to fetch blocked by CORS policy
```
**解決方法:**
- TodoController の @CrossOrigin 設定確認
- フロントエンドのAPIベースURL確認（localhost:8081）

## 開発用コマンド

### フロントエンド
```bash
npm run dev          # 開発サーバー起動
npm run build        # プロダクションビルド
npm run lint         # コード品質チェック
npm run type-check   # TypeScript型チェック
```

### バックエンド
```bash
mvn spring-boot:run  # 開発サーバー起動
mvn clean compile    # コンパイル
mvn test             # テスト実行
mvn clean package    # JARファイル作成
```

## IDE設定推奨

### IntelliJ IDEA（バックエンド）
- **プラグイン**: Spring Boot, Lombok
- **コードスタイル**: Google Java Style
- **実行設定**: Spring Boot Application

### VS Code（フロントエンド）
- **拡張機能**:
  - Vue Language Features (Volar)
  - TypeScript Vue Plugin (Volar)
  - Tailwind CSS IntelliSense
  - ESLint

## Git ワークフロー

### ブランチ戦略
```bash
git checkout -b feature/新機能名
# 開発作業
git add .
git commit -m "feat: 新機能の実装"
git push origin feature/新機能名
```

### コミットメッセージ規約
```
feat: 新機能追加
fix: バグ修正
docs: ドキュメント更新
style: コードスタイル変更
refactor: リファクタリング
test: テスト追加・修正
```

## 本番環境への展開（参考）

### 環境変数設定
```bash
# application.properties (本番環境)
spring.datasource.url=jdbc:postgresql://localhost/todoapp
spring.profiles.active=production
```

### ビルド手順
```bash
# フロントエンド
npm run build

# バックエンド
mvn clean package
java -jar target/todo-api-1.0.0.jar
```

## サポート

### ヘルプ・ドキュメント
- **API仕様**: http://localhost:8081/swagger-ui.html
- **Nuxt3 ドキュメント**: https://nuxt.com/docs
- **Spring Boot ドキュメント**: https://spring.io/projects/spring-boot

### デバッグ情報
- **ログ**: コンソール出力確認
- **ネットワーク**: ブラウザ開発者ツール
- **データベース**: H2 Console で直接確認