/*
 * This file is part of fabric-loom, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2023 FabricMC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.fabricmc.loom.test.unit.architectury

import dev.architectury.loom.metadata.ArchitecturyCommonJson
import dev.architectury.loom.metadata.ModMetadataFile
import dev.architectury.loom.metadata.QuiltModJson
import net.fabricmc.loom.util.ZipUtils
import spock.lang.Specification
import spock.lang.TempDir

import java.nio.file.Path

class ModMetadataFileTest extends Specification {
    @TempDir
    Path zipContents

    @TempDir
    Path workingDir

    def "read nothing"() {
        given:
            def jar = workingDir.resolve("my_mod.jar")
            zipContents.resolve('foo.txt').text = 'hello'
            ZipUtils.pack(zipContents, jar)
        when:
            def modMetadata = ModMetadataFile.fromJar(jar)
        then:
            modMetadata == null
    }

    def "read quilt.mod.json"() {
        given:
            def jar = workingDir.resolve("my_mod.jar")
            zipContents.resolve('quilt.mod.json').text = '{}'
            ZipUtils.pack(zipContents, jar)
        when:
            def modMetadata = ModMetadataFile.fromJar(jar)
        then:
            modMetadata instanceof QuiltModJson
    }

    def "read architectury.common.json"() {
        given:
            def jar = workingDir.resolve("my_mod.jar")
            zipContents.resolve('architectury.common.json').text = '{}'
            ZipUtils.pack(zipContents, jar)
        when:
            def modMetadata = ModMetadataFile.fromJar(jar)
        then:
            modMetadata instanceof ArchitecturyCommonJson
    }
}
