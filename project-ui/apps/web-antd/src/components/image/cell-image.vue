<script setup lang="ts">
import { computed } from 'vue';

import { Image } from 'ant-design-vue';

const ImagePreviewGroup = Image.PreviewGroup;

const props = defineProps<{
  src: string;
}>();

// 解析图片URL列表
const imageUrls = computed(() => {
  if (!props.src) return [];
  if (Array.isArray(props.src)) return props.src;
  if (typeof props.src === 'string' && props.src.includes('||')) {
    return props.src.split('||').map((url) => url.trim());
  }
  return [props.src];
});
</script>

<template>
  <ImagePreviewGroup>
    <template v-for="(url, index) in imageUrls" :key="index">
      <Image
        v-if="index === 0"
        :src="url"
        :style="{ width: '80px', height: '80px' }"
      />
      <Image
        v-else
        :src="url"
        :style="{ position: 'absolute', width: '0', height: '0', overflow: 'hidden' }"
      />
    </template>
  </ImagePreviewGroup>
</template>
