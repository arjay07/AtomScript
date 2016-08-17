@echo off
set FILE=%1

cd %~dp0
title AtomScript - %FILE%
java -jar AtomScript.jar %*