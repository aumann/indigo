#!/usr/bin/env bash

set -e

# Indigo
echo ">>> Indigo (+ Workers)"
cd indigo
bash localpublish.sh
cd ..

# Indigo
echo ">>> Copy Indigo Workers"
cd indigo
cp indigo-render-worker/target/scala-3.0.0-M3/indigo-render-worker-opt.js ../indigo-plugin/indigo-plugin/resources/workers/
cp indigo-render-worker/target/scala-3.0.0-M3/indigo-render-worker-opt.js.map ../indigo-plugin/indigo-plugin/resources/workers/
cp indigo-render-worker/target/scala-3.0.0-M3/indigo-render-worker-opt.js ../indigo-plugin/indigo-plugin/resources/workers/
cp indigo-render-worker/target/scala-3.0.0-M3/indigo-render-worker-opt.js.map ../indigo-plugin/indigo-plugin/resources/workers/
cd ..

# Indigo Plugin
echo ">>> Indigo Plugin"
cd indigo-plugin
bash build.sh
cd ..

# SBT Indigo
echo ">>> SBT-Indigo"
cd sbt-indigo
bash build.sh
cd ..

# Mill Indigo
echo ">>> Mill-Indigo"
cd mill-indigo
bash build.sh
cd ..

