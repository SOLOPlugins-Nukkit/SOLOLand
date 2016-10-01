package com.solo.sololand;

/*
en_US

SOLOLand :: World, Land, Island Management plugin for Nukkit

You can download last version at http://blog.naver.com/solo_5
 
# Dev : This means "Now Developing". UNSTABLE or including a lot of unexpected BUG.



Change Log

Dev-0.0.1
- First build.

Dev-0.0.2
- Now island is generated with tree and grass.
- /땅 정보 command will show land size.
- Added auto save.

Dev-0.0.3
- Fix auto save issue
- Fix /땅 정보 command shows wrong land size

Dev-0.0.4
- actually, island data is no more deleted. just change to blank.

Dev-0.0.5
- /땅 정보 command output will be more simple.

Dev-0.0.6
- Fix wrong spawn point at island.

Dev-0.0.7
- Update IslandGenerator.java
- Fix anyone execute /섬 시작 command.




ko_KR

SOLOLand :: 누킷을 위한 월드, 땅, 섬 관리 플러그인 입니다.

http://blog.naver.com/solo_5 에서 최신 버전을 다운로드 받을 수 있습니다.

# Dev : 개발중임을 의미합니다. 불안정하며, 예상치 못한 버그가 있을 수 있습니다.



Change Log

Dev-0.0.1
- 첫 빌드

Dev-0.0.2
- 섬에 잔디, 나무 추가
- /땅 정보 > 땅 크기 추가
- 자동 저장 추가

Dev-0.0.3
- 자동 저장 문제 해결
- 땅 크기가 잘못 표시되는 문제 해결

Dev-0.0.4
- 섬 삭제(/땅 삭제)시 데이터는 보존하되 땅 정보를 비우도록 수정

Dev-0.0.5
- /땅 정보 입력시 최대한 간결하게 표시하도록 수정

Dev-0.0.6
- 섬 스폰이 잘못 설정되던 점 수정

Dev-0.0.7
- 섬 제너레이터 수정
- /섬 시작 명령어 권한 수정

Dev-0.1.0
- 월드 커스텀 이름 추가
- /월드 이름변경 [바꿀 이름] 명령어 추가
- 커스텀 이름 추가에 맞춰, 많은 파일을 수정
- /땅 목록 입력 시 모든 월드에 소유한 땅 표시

Dev-0.1.1
- 오피는 돈이 없어도 땅 생성 가능하도록 변경

Dev-0.2.0
- 코드 업데이트
- /땅 확장 추가
- 다른 사람이 내 땅 방문 시 주인에게 알림 (Thanks to EVE(jmjm1208) giving idea)
- /땅 방문자 - 현재 땅에 사람 목록 (Thanks to 히륜(i dont know his naver id) giving idea)
- 패키지 경로 수정
- 땅에서 나갈 시 팝업으로 표시
- 환영파티클 용암, 불 추가
- 출입금지 상태에서 접근 시 파티클 도배 되는 점 수정하기
- 월드 불번짐 옵션 추가 (/월드 불번짐허용) [Bug:작동안함]

Dev-0.3.0
- 평야 제너레이터 추가 (GridLand)
- 텅 빈 월드 제너레이터 추가 (EmptyWorld)
- 스카이블럭 제너레이터 추가 (SkyBlock)
- /섬 가격 [가격] 명령어 추가
- /땅 공유목록 명령어 추가
- /월드 설정정보 명령어 추가
- /월드 유저정보 명령어 추가
- /월드 땅정보 명령어 추가

TODO:
- 파티클 더 많이 추가하기
- 메세지 디자인 변경
- 활 허용 (x)
- /땅 합치기 추가
- /방 추가

*/

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.level.Level;
import cn.nukkit.level.generator.Generator;

import com.solo.sololand.world.World;
import com.solo.sololand.world.Island;
import com.solo.sololand.generator.IslandGenerator;
import com.solo.sololand.generator.GridLandGenerator;
import com.solo.sololand.generator.EmptyWorldGenerator;
import com.solo.sololand.generator.SkyBlockGenerator;
 
import com.solo.sololand.task.AutoSaveTask;

public class Main extends PluginBase {

  private EventListener listener = new EventListener(this);
  private MainCommand command = new MainCommand(this);

  @Override
  public void onLoad(){
    Generator.addGenerator(IslandGenerator.class, "island", IslandGenerator.TYPE_ISLAND);
    Generator.addGenerator(GridLandGenerator.class, "gridland", GridLandGenerator.TYPE_GRID_LAND);
    Generator.addGenerator(EmptyWorldGenerator.class, "emptyworld", EmptyWorldGenerator.TYPE_EMPTY_WORLD);
    Generator.addGenerator(SkyBlockGenerator.class, "skyblock", SkyBlockGenerator.TYPE_SKY_BLOCK);
  }

  @Override
  public void onEnable(){
    File islandFolder = new File(Server.getInstance().getDataPath() + File.separator + "island");
    File gridlandFolder = new File(Server.getInstance().getDataPath() + File.separator + "gridland");
    File skyblockFolder = new File(Server.getInstance().getDataPath() + File.separator + "skyblock");
    if(islandFolder.isDirectory()){
      this.getServer().getInstance().loadLevel("island");
    }
    if(gridlandFolder.isDirectory()){
      this.getServer().getInstance().loadLevel("gridland");
    }
    if(skyblockFolder.isDirectory()){
      this.getServer().getInstance().loadLevel("skyblock");
    }
    for(Level level : this.getServer().getLevels().values()){
      if(level.getFolderName().equals("island")){
        World.registerWorld(new Island(level));
      }else{
        World.registerWorld(new World(level));
      }
    }

this.getServer().getPluginManager().registerEvents(this.listener, this);
    this.getServer().getScheduler().scheduleRepeatingTask(new AutoSaveTask(this), 12000);
  }

  @Override
  public void onDisable() {
    for(World world : World.getAll().values()){
      world.save();
    }
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    return this.command.onCommand(sender, command, args);
  }
}
