# 🎨 UI/UX デザイン仕様書

## デザインコンセプト

**「Purr-fectly organized tasks」**  
猫をテーマにした、親しみやすく直感的なTODO管理アプリ

## ブランディング

### ロゴ・アイコン
- **メインロゴ**: 猫のシルエット（SVG）
- **カラー**: グレー系（#374151）
- **位置**: ヘッダー左側、タイトル横

### テーマカラー

| 用途 | カラーコード | Tailwind Class | 説明 |
|------|-------------|----------------|------|
| メイン背景 | #F9FAFB | `bg-gray-50` | ライトグレー |
| カード背景 | #FFFFFF | `bg-white` | 純白 |
| プライマリ | #3B82F6 | `bg-blue-500` | 青色（アクション） |
| 成功 | #10B981 | `bg-green-500` | 緑色（保存・完了） |
| 警告 | #F59E0B | `bg-yellow-500` | 黄色（注意） |
| エラー | #EF4444 | `bg-red-500` | 赤色（期限切れ・削除） |
| テキスト | #374151 | `text-gray-700` | ダークグレー |

## レイアウト構成

### ページ構造
```
┌─────────────────────────────────────┐
│ Header (Logo + Title)               │
├─────────────────────────────────────┤
│ Add Todo Form                       │
│ ┌─ Title Input ──┬─ Add Button ─┐   │
│ ├─ Description ──────────────────┤   │
│ └─ Due Date ─────┬─ 1Week Btn ──┘   │
├─────────────────────────────────────┤
│ Todo List                           │
│ ┌─⋮⋮─┬─☑─┬─ Content ─┬─ Actions ─┐ │
│ └────┴───┴──────────┴──────────┘   │
└─────────────────────────────────────┘
```

## コンポーネント設計

### 1. ヘッダー (Header)
- **猫ロゴ**: SVGアイコン (40px × 40px)
- **タイトル**: "Cat Todo" (text-3xl, font-bold)
- **サブタイトル**: "Purr-fectly organized tasks" (text-sm)

### 2. TODOフォーム (Add Todo Form)
```css
/* レスポンシブ対応 */
.form-container {
  max-width: 672px; /* max-w-2xl */
  margin: 0 auto;
  padding: 24px;
}

/* フィールドスタイル */
.input-field {
  border: 1px solid #D1D5DB; /* border-gray-300 */
  border-radius: 8px; /* rounded-lg */
  padding: 8px 16px;
  focus: {
    outline: none;
    ring: 2px #3B82F6; /* focus:ring-blue-500 */
    border-color: transparent;
  }
}
```

### 3. TODOアイテム (Todo Item)
```css
.todo-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
  border: 1px solid #E5E7EB;
  transition: box-shadow 150ms;
}

.todo-item:hover {
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
}
```

#### ドラッグハンドル
- **アイコン**: ⋮⋮ (縦3点リーダー)
- **サイズ**: text-xs
- **カラー**: gray-400 → gray-600 (hover)
- **カーソル**: grab → grabbing

#### チェックボックス
- **サイズ**: 20px × 20px
- **カラー**: blue-600 (チェック時)
- **背景**: gray-100

#### コンテンツエリア
- **タイトル**: font-medium, text-gray-900
- **説明**: text-sm, text-gray-600
- **期限**: text-xs, 📅アイコン付き
  - 未来: text-blue-500
  - 期限切れ: text-red-500 + "(Overdue)"

#### アクションボタン
- **編集**: ✏️ (text-blue-500)
- **削除**: 🗑️ (text-red-500)

## 状態別スタイリング

### 完了済みTODO
```css
.completed {
  text-decoration: line-through;
  color: #9CA3AF; /* text-gray-400 */
}
```

### 期限切れ表示
```css
.overdue {
  color: #EF4444; /* text-red-500 */
}

.overdue-label {
  color: #EF4444;
  font-weight: 500;
}
```

### ドラッグ&ドロップ状態
```css
.sortable-ghost {
  opacity: 0.5;
  background: #F3F4F6;
  border: 2px dashed #D1D5DB;
}

.sortable-chosen {
  cursor: grabbing;
}

.sortable-drag {
  opacity: 0.8;
  transform: rotate(2deg);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
}
```

## レスポンシブデザイン

### ブレークポイント
| デバイス | 幅 | Tailwind | 対応 |
|----------|----|---------|----|
| Mobile | < 640px | default | ✅ |
| Tablet | ≥ 640px | `sm:` | ✅ |
| Desktop | ≥ 768px | `md:` | ✅ |

### レスポンシブ対応
```css
/* モバイル: 縦積み */
.form-layout {
  flex-direction: column;
}

/* タブレット以上: 横並び */
@media (min-width: 640px) {
  .form-layout {
    flex-direction: row;
  }
}
```

## アニメーション

### 基本トランジション
```css
.transition-base {
  transition: all 150ms ease-in-out;
}

.hover-shadow {
  transition: box-shadow 150ms;
}

.hover-shadow:hover {
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
}
```

### ドラッグアニメーション
- **移動**: 150ms ease-in-out
- **回転**: 2度の微回転
- **影**: 強めのドロップシャドウ

## アクセシビリティ

### フォーカス管理
```css
.focus-ring {
  focus: {
    outline: none;
    ring: 2px #3B82F6;
    ring-offset: 2px;
  }
}
```

### カラーコントラスト
- **テキスト**: WCAG AA準拠
- **ボタン**: 十分なコントラスト比確保
- **状態表示**: 色だけでなくアイコンでも区別

## アイコン一覧

| 機能 | アイコン | 説明 |
|------|---------|------|
| ロゴ | 🐱 | 猫のシルエット（SVG） |
| ドラッグ | ⋮⋮ | 縦3点リーダー |
| 期限 | 📅 | カレンダー |
| 編集 | ✏️ | 鉛筆 |
| 削除 | 🗑️ | ゴミ箱 |
| 追加 | ➕ | プラス（ボタン内テキスト） |

## 実装上の考慮点

1. **パフォーマンス**: CSS トランジションで滑らかな操作感
2. **ユーザビリティ**: ホバー状態での視覚的フィードバック
3. **一貫性**: Tailwind クラスベースで統一感確保
4. **保守性**: コンポーネント単位での スタイル管理