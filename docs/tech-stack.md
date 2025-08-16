# 🛠 技術スタック仕様書

## フロントエンド

### メインフレームワーク
- **Nuxt3**: Vue.jsベースのフルスタックフレームワーク
- **Vue.js 3**: Composition API使用
- **TypeScript**: 型安全な開発

### スタイリング
- **TailwindCSS**: ユーティリティファーストCSSフレームワーク
- **レスポンシブデザイン**: Mobile First
- **カスタムCSS**: ドラッグ&ドロップ用スタイル

### ライブラリ
- **SortableJS**: ドラッグ&ドロップ機能
- **@vueuse/integrations**: Vue3統合ライブラリ

## バックエンド

### メインフレームワーク
- **Spring Boot 3.2.0**: Javaアプリケーションフレームワーク
- **Java 17**: LTS版使用
- **Maven**: 依存関係管理・ビルドツール

### データアクセス
- **Spring Data JPA**: ORM層
- **H2 Database**: インメモリデータベース（開発用）
- **Hibernate**: JPA実装

### API設計
- **Spring Web MVC**: REST API
- **SpringDoc OpenAPI**: Swagger/OpenAPI 3対応
- **Jackson**: JSON シリアライゼーション

## 開発ツール

### ビルド・実行
- **Maven**: バックエンドビルド
- **npm**: フロントエンドパッケージ管理
- **Vite**: フロントエンドビルドツール（Nuxt3内蔵）

### 開発環境
- **Hot Reload**: 開発時の自動リロード
- **TypeScript**: 型チェック
- **ESLint**: コード品質チェック

## アーキテクチャ

### システム構成
```
[Frontend: Nuxt3] ←→ [Backend: Spring Boot] ←→ [Database: H2]
     ↓                        ↓
[TailwindCSS]           [Spring Data JPA]
[SortableJS]            [Swagger UI]
```

### ポート設定
- **Frontend**: 3000
- **Backend**: 8081
- **H2 Console**: 8081/h2-console

### 通信
- **REST API**: JSON形式
- **CORS**: localhost:3000 ⇔ localhost:8081
- **HTTP**: 開発環境（本番ではHTTPS推奨）

## 依存関係

### フロントエンド (package.json)
```json
{
  "dependencies": {
    "@nuxtjs/tailwindcss": "^6.14.0",
    "@vueuse/integrations": "^13.6.0",
    "nuxt": "^4.0.3",
    "sortablejs": "^1.15.6",
    "vue": "^3.5.18",
    "vue-router": "^4.5.1"
  }
}
```

### バックエンド (pom.xml)
```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
  </dependency>
  <dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
  </dependency>
  <dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.2.0</version>
  </dependency>
</dependencies>
```

## 環境要件

### 開発環境
- **Java**: 17以上
- **Node.js**: 18以上
- **Maven**: 3.6以上
- **Git**: 最新版

### 推奨IDE
- **IntelliJ IDEA**: Java開発
- **VS Code**: フロントエンド開発
- **拡張機能**: Vue, TypeScript, TailwindCSS