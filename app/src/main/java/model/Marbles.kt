package model

/**
 * Created by zpf on 2018/1/24.
 * Tank反射出去的弹珠的左、上、右、下坐标
 * @param left 子弹的坐标左
 * @param top 子弹的坐标上
 * @param right 子弹的坐标右
 * @param btm 子弹的坐标下
 * @param isDestroy 子弹的是否销毁（超出屏幕或者击中目标）
 */
class Marbles constructor(var left: Float,
                          var top: Float,
                          var right: Float,
                          var btm: Float,
                          var isDestroy: Boolean)