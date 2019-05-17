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


if [ $# -lt 2 ]; then
    echo "usage: $0 <features> <lars-url> [<username> <password>]"
    exit 1
fi

feature=$1
larsURL=$2
username=$3
password=$4

if [ -z "${username}" ]; then
    echo "No username provided, using 'admin' ..."
    username="admin"
fi

if [ -z "${password}" ]; then
    echo "No password provided, using 'adminPwd' ..."
    password="adminPwd"
fi

installUtility download ${feature} --acceptLicense --verbose --location=/tmp/bundle
~/larsClient/bin/larsClient upload /tmp/bundle/features/* --url=${larsURL}/ma/v1 --username=${username} --password=${password}
rm -rf /tmp/bundle
