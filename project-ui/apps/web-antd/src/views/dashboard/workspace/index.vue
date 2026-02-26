<script lang="ts" setup>
import { ref } from 'vue';

import { WorkbenchHeader } from '@vben/common-ui';
import { preferences } from '@vben/preferences';
import { useUserStore } from '@vben/stores';

const userStore = useUserStore();
const appTitle = ref(import.meta.env.VITE_APP_TITLE);
</script>

<template>
  <div class="p-5">
    <WorkbenchHeader
      :avatar="userStore.userInfo?.avatar || preferences.app.defaultAvatar"
    >
      <template #title>
        你好, {{ userStore.userInfo?.nickname }}, 开始您一天的工作吧！
      </template>
      <template #description> 欢迎使用，{{ appTitle }}！ </template>
    </WorkbenchHeader>
  </div>
</template>
