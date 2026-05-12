import { createRouter, createWebHistory } from 'vue-router';

import ListTask from "../components/ListTask.vue";
import FormTask from "../components/FormTask.vue";

const routes = [
  {
    path: '/',
    name: 'list',
    component: ListTask
  },
  {
    path: '/form/:id?',
    name: 'form',
    component: FormTask,
    props: true
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

export default router;
