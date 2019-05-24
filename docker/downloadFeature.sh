#!/bin/bash
# (C) Copyright IBM Corporation 2016.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


if [ $# -lt 1 ]; then
    echo "usage: $0 <feature> [<target-dir>]"
    exit 1
fi

feature=$1
targetdir=$2

if [ -z "${targetdir}" ]; then
  targetdir=/tmp/bundle
fi
echo "downloading features to ${targetdir}"

installUtility download ${feature} --acceptLicense --verbose --location=${targetdir}
