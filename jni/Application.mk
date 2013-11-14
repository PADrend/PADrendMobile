#
# This file is part of the PADrendMobile project.
# Web page: http://www.padrend.de/
# Copyright (C) 2010-2013 Benjamin Eikel <benjamin@eikel.org>
#
# This project is subject to the terms of the Mozilla Public License, v. 2.0.
# You should have received a copy of the MPL along with this project; see the
# file LICENSE. If not, you can obtain one at http://mozilla.org/MPL/2.0/.
#

#APP_OPTIM := release
#APP_ABI := armeabi-v7a
APP_STL := gnustl_shared
APP_CPPFLAGS := -fexceptions -frtti -std=c++11
NDK_TOOLCHAIN_VERSION := 4.8
NDK_MODULE_PATH := $(call my-dir)/ThirdParty
