/*
 * QAuxiliary - An Xposed module for QQ/TIM
 * Copyright (C) 2019-2023 QAuxiliary developers
 * https://github.com/cinit/QAuxiliary
 *
 * This software is non-free but opensource software: you can redistribute it
 * and/or modify it under the terms of the qwq233 Universal License
 * as published on https://github.com/qwq233/license; either
 * version 2 of the License, or any later version and our EULA as published
 * by QAuxiliary contributors.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the qwq233 Universal License for more details.
 *
 * See
 * <https://github.com/qwq233/license>
 * <https://github.com/cinit/QAuxiliary/blob/master/LICENSE.md>.
 */

package com.xiaoniu.hook

import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import io.github.qauxv.base.annotation.FunctionHookEntry
import io.github.qauxv.base.annotation.UiItemAgentEntry
import io.github.qauxv.dsl.FunctionEntryRouter
import io.github.qauxv.hook.CommonSwitchFunctionHook
import io.github.qauxv.util.dexkit.DexKit
import io.github.qauxv.util.dexkit.QQSettingMeABTestHelper_isV9ExpGroup
import io.github.qauxv.util.dexkit.QQSettingTypeManager_isEnableMeTabFromExp
import xyz.nextalone.util.throwOrTrue

@FunctionHookEntry
@UiItemAgentEntry
object DisableQQSettingMeV9 : CommonSwitchFunctionHook(
    targets = arrayOf(
        QQSettingMeABTestHelper_isV9ExpGroup,
        QQSettingTypeManager_isEnableMeTabFromExp,
    )
) {

    override val name = "禁止新样式侧滑栏"

    override val uiItemLocation: Array<String> = FunctionEntryRouter.Locations.Simplify.SLIDING_UI

    override fun initOnce() = throwOrTrue {
        // QQ_9_2_66+ uses MMKV-based experiment flag QQSettingTypeManager.isEnableMeTabFromExp
        // Older versions use AB test method QQSettingMeABTestHelper.isV9ExpGroup
        DexKit.loadMethodFromCache(QQSettingTypeManager_isEnableMeTabFromExp)?.hookReturnConstant(false)
        DexKit.loadMethodFromCache(QQSettingMeABTestHelper_isV9ExpGroup)?.hookReturnConstant(false)
    }
}
