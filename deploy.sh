#!/bin/bash

lein clean
lein cljsbuild once min
firebase deploy
