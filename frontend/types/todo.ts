export interface Todo {
  id: number;
  title: string;
  description?: string;
  completed: boolean;
  createdAt: string;
  dueDate?: string;
  displayOrder: number;
}