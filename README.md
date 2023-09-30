# Binary Search Tutorial

## TL;DR

### Template

```java
l = 答案下界; // 闭区间
r = 答案上界; // 闭区间
while (l <= r) {
    mid = (l + r) / 2;
    if (mid 满足要求) {
        /**
         * r = mid - 1 和 l = mid + 1 两条语句二选一
         * 如果要找 最早/最靠前/最小 满足要求的值, 就往左边走, 选 r = mid - 1
         * 否则如果要找 最晚/最靠后/最大 满足要求的值, 就往右边走, 选 l = mid + 1
         */
    } else {
        /* 选上面的另一条语句 */
    }
}
return /* else 块中移动的指针 (else 块中移动的指针必定是答案) */;
```

**Note**

如果 $[答案下界, 答案上界]$ 中没有一个值满足要求，则
* 如果找的是最早/最靠前/最小的答案，则答案指针停在$答案下界-1$处
* 如果找的是最晚/最靠后/最大的答案，则答案指针停在$答案上界+1$处

### Example

```java
/* 找到第一个 >= key 的数字 */
int binary_search(int[] arr, int key) {
    int l = 0;
    int r = arr.length - 1;
    while (l <= r) {
        int mid = (l + r) >> 1;
        if (arr[mid] >= key) { /* mid 满足要求, 但要找第一个满足要求的, 所以要继续往左 */
            r = mid - 1;
        } else {
            l = mid + 1; /* 选另一条语句, 往右 */
        }
    }
    return l; /* else 中移动了 l 指针, 故结束后 l 即为答案
}
```

## Why?

[Read the details here](./DETAILS.md)